package org.androidpn.client;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import android.content.Intent;
import android.util.Log;

public class NotificationPacketListener implements PacketListener {

    private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationPacketListener.class);

    private final XmppManager xmppManager;

    public NotificationPacketListener(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

    @Override
    public void processPacket(Packet packet) {
        Log.d(LOGTAG, "NotificationPacketListener.processPacket()...");
        Log.d(LOGTAG, "packet.toXML()=" + packet.toXML());

        if (packet instanceof NotificationIQ) {
            NotificationIQ notification = (NotificationIQ) packet;

            if (notification.getChildElementXML().contains(
                    "androidpn:iq:notification")) {
                String notificationId = notification.getId();
                String notificationApiKey = notification.getApiKey();
                String notificationTitle = notification.getTitle();
                String notificationMessage = notification.getMessage();
                //                String notificationTicker = notification.getTicker();
                String notificationUri = notification.getUri();
                String notificationFrom = notification.getFrom();
                String packetId = notification.getPacketID();

                
                //Handle remote wipe case title = 'REMOTE_WIPE'
                if (Constants.REMOTE_WIPE.equals(notificationTitle)) {
					Log.i("got remote wipe instruction","");
					//go handle 
					//TODO
				} else {
					//update content
				}
                
                Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
                intent.putExtra(Constants.NOTIFICATION_ID, notificationId);
                intent.putExtra(Constants.NOTIFICATION_API_KEY,
                        notificationApiKey);
                intent.putExtra(Constants.NOTIFICATION_TITLE,
                                notificationTitle);
                intent.putExtra(Constants.NOTIFICATION_MESSAGE,
                        notificationMessage);
                intent.putExtra(Constants.NOTIFICATION_URI, notificationUri);
                intent.putExtra(Constants.NOTIFICATION_FROM, notificationFrom);
                intent.putExtra(Constants.PACKET_ID, packetId);
                
                //TODO
                IQ result = NotificationIQ.createResultIQ(notification);
                
                try{
                	xmppManager.getConnection().sendPacket(result);
                }catch(Exception e){}
                
                xmppManager.getContext().sendBroadcast(intent); 
            }
        }

    }
}
