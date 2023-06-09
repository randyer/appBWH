package edu.wpi.teamc.dao.displays.signage;

import edu.wpi.teamc.dao.HospitalSystem;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.Getter;

public class SignSystem {
  @Getter private HashMap<String, Sign> signs;

  public SignSystem(HashMap<String, Sign> signs) {
    this.signs = signs;
  }

  public SignSystem() {
    signs = new HashMap<>();
    ArrayList<SignEntry> s = (ArrayList<SignEntry>) HospitalSystem.fetchAllObjects(new SignEntry());
    for (SignEntry signEntry : s) {
      if (signs.containsKey(signEntry.getMacadd())) {
        signs.get(signEntry.getMacadd()).addSignEntry(signEntry);
      } else {
        Sign sign = new Sign();
        sign.setMacadd(signEntry.getMacadd());
        sign.setDevicename(signEntry.getDevicename());
        sign.addSignEntry(signEntry);
        signs.put(signEntry.getMacadd(), sign);
      }
    }
  }

  public void removeSignVersion(SignVersion selected) {
    Date date = selected.getDate();
    SignEntryDao signEntryDao = new SignEntryDao();
    signEntryDao.deleteVersion(selected.getSignEntries().get(0).macadd, date);
    signs
        .get(selected.getSignEntries().get(0).macadd)
        .removeSignEntryVersion(selected.getSignEntries().get(0));
    if (signs.get(selected.getSignEntries().get(0).macadd).getSignVersions().isEmpty()) {
      signs.remove(selected.getSignEntries().get(0).macadd);
    }
  }

  public void addSignVersion(SignVersion newVersion) {
    SignEntryDao signEntryDao = new SignEntryDao();
    for (SignEntry signEntry : newVersion.getSignEntries()) {
      signEntryDao.addRow(signEntry);
    }
  }

  public void updateMacAddress(String oldmac, String newmac) {
    if (!signs.containsKey(newmac)) {
      SignEntryDao signEntryDao = new SignEntryDao();
      signEntryDao.updateMacAddress(oldmac, newmac);
    } else {
      throw new IllegalArgumentException("Mac Address already exists");
    }
  }

  public void updateDeviceName(String mac, String newname) {
    SignEntryDao signEntryDao = new SignEntryDao();
    signEntryDao.updateDeviceName(mac, newname);
  }
}
