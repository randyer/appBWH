package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

public class FlowerDeliveryRequest extends AbsServiceRequest {
  @Getter private String flower;
  @Getter @Setter private String eta;

  FlowerDeliveryRequest(
      int requestID, Requester requester, String roomName, String flower, String additionalNotes) {
    super(requestID, requester, roomName, additionalNotes);
    this.flower = flower;
  }

  public FlowerDeliveryRequest(
      Requester requester, String roomName, String flower, String additionalNotes) {
    super(requester, roomName, additionalNotes);
    this.flower = flower;
  }

  @Override
  void executeRequest() {}
}
