package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class OnlineModel {
  private Socket socket;
  private DataOutputStream dos;
  private DataInputStream dis;
  private boolean unableToCommunicate = false;

  private String waitingString = "Waiting for another player";
  private String unableToCommunicateWithOpponentString = "Unable to communicate to opponent";

}
