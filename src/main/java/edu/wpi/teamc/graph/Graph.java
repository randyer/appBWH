package edu.wpi.teamc.graph;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.map.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Graph {
  protected Map<Integer, GraphNode> nodes = new HashMap<>();
  protected Set<GraphNode> settled = new HashSet<>();
  protected Set<GraphNode> unsettled = new HashSet<>();
  protected Map<GraphNode, GraphNode> nodeBefore = new HashMap<>();
  protected Map<GraphNode, Double> distance = new HashMap<>();
  protected Map<GraphNode, String> nodeType = new HashMap<>();
  protected Map<Integer, String> nodeIDtoLongName = new HashMap<>();
  protected Map<String, LinkedList<Move>> longNameToMoves = new HashMap<>();
  protected HashMap<String, Integer> longNameToNodeID = new HashMap<>();
  protected Map<Integer, Date> nodeIDtoLastDate = new HashMap<>();
  protected Map<String, String> longNameToNodeType = new HashMap<>();
  protected PriorityQueue<GraphNode> pq;
  protected Move prev = null;
  protected final double DIST_DEFAULT = Double.POSITIVE_INFINITY;
  private IAlgorithm algo;
  private boolean doStair;
  private boolean trigger = false;

  /** Empty Constructor for Graph */
  public Graph() {}

  public Graph(String algoChoice, boolean doStair) {
    if (algoChoice.equals("A*")) {
      algo = new AStar();
    } else if (algoChoice.equals("BFS")) {
      algo = new BFS();
    } else if (algoChoice.equals("DFS")) {
      algo = new DFS();
    } else {
      algo = new Dijkstra();
    }
    this.doStair = doStair;
  }

  /**
   * A helper method to populate the HashMaps with move information
   *
   * @param move
   */
  public void massPutMove(Move move) {
    nodeIDtoLastDate.put(move.getNodeID(), move.getDate());
    longNameToNodeID.put(move.getLongName(), move.getNodeID());
    nodeIDtoLongName.put(move.getNodeID(), move.getLongName());
  }

  public void syncMoves(String date) {
    List<Move> moves = new LinkedList<>();
    nodeIDtoLastDate.clear();
    longNameToNodeID.clear();
    nodeIDtoLongName.clear();
    longNameToMoves.clear();

    try {
      moves = (List<Move>) HospitalSystem.fetchAllObjects(new Move());
    } catch (Exception e) {
      // error
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date locDate;
    try {
      locDate = format.parse(date);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }

    Date dateObj = new Date(locDate.getTime());

    for (Move move : moves) {
      if (longNameToMoves.get(move.getLongName()) == null) {
        LinkedList<Move> temp = new LinkedList<>();
        temp.add(move);
        longNameToMoves.put(move.getLongName(), temp);
      } else {
        longNameToMoves.get(move.getLongName()).add(move);
      }
      // store the move for the desired date
      if (move.getDate().equals(dateObj)) {
        massPutMove(move);
      } else if (move.getDate().compareTo(dateObj) < 0) {
        nodeIDtoLastDate.putIfAbsent(move.getNodeID(), move.getDate());
        nodeIDtoLongName.putIfAbsent(move.getNodeID(), move.getLongName());
        longNameToNodeID.putIfAbsent(move.getLongName(), move.getNodeID());

        // if date is more recent than the one currently stored
        if (nodeIDtoLastDate.get(move.getNodeID()).compareTo(move.getDate()) < 0) {
          massPutMove(move);
        }
      }
    }
  }
  /**
   * A method to sync the graph with stored DB data
   *
   * @param date Date to sync with (specifically for move component)
   */
  public void syncWithDB(String date) {
    List<Node> nodes = new LinkedList<>();
    List<Edge> edges = new LinkedList<>();
    List<LocationName> locs = new LinkedList<>();

    try {
      nodes = (List<Node>) HospitalSystem.fetchAllObjects(new Node());
      edges = (List<Edge>) HospitalSystem.fetchAllObjects(new Edge(1, 1));
      locs = (List<LocationName>) HospitalSystem.fetchAllObjects(new LocationName());
    } catch (Exception e) {
      // error
    }

    syncMoves(date);

    for (LocationName loc : locs) {
      longNameToNodeType.put(loc.getLongName(), loc.getNodeType());
    }

    for (Node node : nodes) {
      String longName = nodeIDtoLongName.get(node.getNodeID());
      String nodeType = longNameToNodeType.get(longName);

      addNode(
          new GraphNode(
              node.getNodeID(),
              node.getXCoord(),
              node.getYCoord(),
              node.getFloor(),
              node.getBuilding(),
              nodeType,
              node.getStatus()));
    }

    for (Edge edge : edges) {
      GraphNode src = this.nodes.get(edge.getStartNode());
      GraphNode dest = this.nodes.get(edge.getEndNode());
      String origID = src.getNodeID() + "_" + dest.getNodeID();
      String reverseID = dest.getNodeID() + "_" + src.getNodeID();

      if (dest.getStatus().equals(NODE_STATUS.OPEN)) {
        if (doStair) {
          src.getGraphEdges().add(new GraphEdge(origID, src, dest));
          dest.getGraphEdges().add(new GraphEdge(reverseID, dest, src));
        } else {
          if (!src.getNodeType().equals("STAI") || !dest.getNodeType().equals("STAI")) {
            src.getGraphEdges().add(new GraphEdge(origID, src, dest));
            dest.getGraphEdges().add(new GraphEdge(reverseID, dest, src));
          }
        }
      }
    }
  }

  /**
   * Adds a node to the map
   *
   * @param node - node to be added
   */
  public void addNode(GraphNode node) {
    // check if node already exists
    if (nodes.containsKey(node.getNodeID())) {
      System.out.println("Node already exists");
    } else {
      nodes.put(node.getNodeID(), node);
    }
  }

  /**
   * Get the path between a src and dest node using astar
   *
   * @param src source node
   * @param dest destination node
   */
  public List<GraphNode> getPathway(GraphNode src, GraphNode dest) {
    return algo.getDirections(src, dest);
  }

  /**
   * Get the neighboring nodes of a node, if they have not been settled
   *
   * @param node
   * @return
   */
  protected List<GraphNode> getNeighbors(GraphNode node) {
    List<GraphNode> neighbors = new ArrayList<>();

    for (GraphEdge edge : node.getGraphEdges()) {
      // check to make sure dest node isn't in settled, don't want to go to a settled node
      if (!settled.contains(edge.getDest())) {
        neighbors.add(edge.getDest());
      }
    }

    return neighbors;
  }

  /**
   * Get the distance between two given nodes, if an edge between them exists
   *
   * @param node source node
   * @param target dest node
   * @return the distance
   */
  protected double getDistance(GraphNode node, GraphNode target) {
    for (GraphEdge edge : node.getGraphEdges()) {
      if (edge.getSrc().equals(node) && edge.getDest().equals(target)) {
        return edge.getWeight();
      }
    }

    return -1;
  }

  /**
   * Return the path to the target node
   *
   * @param target
   * @return the path
   */
  protected List<GraphNode> getPath(GraphNode target) {
    List<GraphNode> path = new LinkedList<>();
    GraphNode node = target;

    // keep going down until there is no nodeBefore
    while (node != null) {
      path.add(node);
      node = nodeBefore.get(node);
    }

    // reverse the list to go in correct direction
    Collections.reverse(path);

    return path;
  }

  public GraphNode getNode(int nodeID) {
    return nodes.get(nodeID);
  }

  public int getNodeIDfromLongName(String longName) {
    return longNameToNodeID.get(longName);
  }

  public String getLongNameFromNodeID(int nodeID) {
    return nodeIDtoLongName.get(nodeID);
  }

  public String checkRecentMoves(int src, int dest, LocalDate date) {
    String s = "";

    if (longNameToMoves.get(nodeIDtoLongName.get(src)).size() > 1
        || longNameToMoves.get(nodeIDtoLongName.get(dest)).size() > 1) {
      int srcVal = dateToInt(nodeIDtoLastDate.get(src).toLocalDate());
      int destVal = dateToInt(nodeIDtoLastDate.get(dest).toLocalDate());
      int currVal = dateToInt(date);
      int diffSrc = srcVal - currVal;
      int diffDest = destVal - currVal;
      int withinAmount = 3;

      if (-withinAmount <= diffSrc && diffSrc <= 0) {
        s =
            "NOTICE : "
                + " Your starting location has moved to where "
                + getPrevLocName(src, date)
                + " was "
                + -diffSrc
                + " days ago.";
      }

      if (-withinAmount <= diffDest && diffDest <= 0) {
        s =
            "NOTICE : "
                + " Your end location has moved to where "
                + getPrevLocName(dest, date)
                + " was "
                + -diffDest
                + " days ago.";
      }
    }

    return s;
  }

  public String checkUpcomingMoves(String src, String dest, LocalDate date) {
    String s = "";

    if (longNameToMoves.get(src).size() > 1 || longNameToMoves.get(dest).size() > 1) {
      Move start = findUpcomingMove(src, date);
      // Move end = findUpcomingMove(dest, date);
      int srcVal = dateToInt(start.getDate().toLocalDate());
      // int destVal = dateToInt(end.getDate().toLocalDate());
      int currVal = dateToInt(date);
      int diffSrc = srcVal - currVal;
      // int diffDest = destVal - currVal;
      int withinAmount = 3;

      if (withinAmount >= diffSrc && diffSrc >= 0) {
        s =
            "NOTICE : "
                + " Your starting location will move to where "
                + nodeIDtoLongName.get(start.getNodeID())
                + " is in "
                + diffSrc
                + " day(s).";
      }

      //      if (-withinAmount <= diffDest && diffDest <= 0) {
      //        s =
      //                "NOTICE : "
      //                        + " Your end location has moved to where "
      //                        + getPrevLocName(dest, date)
      //                        + " was "
      //                        + -diffDest
      //                        + " days ago.";
      //      }
    }

    return s;
  }

  public int dateToInt(LocalDate date) {
    String[] dateArr = date.toString().split("-");
    int[] dateIntArr = {
      Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2])
    };
    return dateIntArr[0] * 12 * 31 + dateIntArr[1] * 31 + dateIntArr[2];
  }

  public String getPrevLocName(int node, LocalDate date) {
    HashMap<LocalDate, String> dateToName = new HashMap<>();
    LinkedList<LocalDate> trimmed = new LinkedList<>();
    String s = "";

    for (Move m : longNameToMoves.get(nodeIDtoLongName.get(node))) {
      if (!m.getDate().toLocalDate().isAfter(date)) {
        trimmed.add(m.getDate().toLocalDate());
        dateToName.put(m.getDate().toLocalDate(), m.getLongName());
      }
    }

    LocalDate first = Collections.max(trimmed);
    trimmed.remove(first);

    LocalDate second = Collections.max(trimmed);

    return dateToName.get(second);
  }

  public Move findUpcomingMove(String nodeID, LocalDate date) {
    HashMap<LocalDate, Move> dateToMove = new HashMap<>();
    LinkedList<LocalDate> trimmed = new LinkedList<>();
    String s = "";

    for (Move m : longNameToMoves.get(nodeID)) {
      if (m.getDate().toLocalDate().isAfter(date)) {
        trimmed.add(m.getDate().toLocalDate());
        dateToMove.put(m.getDate().toLocalDate(), m);
      }
    }

    LocalDate first = Collections.min(trimmed);

    return dateToMove.get(first);
  }
}
