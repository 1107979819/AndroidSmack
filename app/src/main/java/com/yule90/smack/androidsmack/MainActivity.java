package com.yule90.smack.androidsmack;

import android.app.Activity;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearch;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.util.Collection;


/**
 * xmpp 通讯 Smack
 */
public class MainActivity extends Activity {

    private  XMPPTCPConnection mConnection = null;
    private final String TAG = "Test";

    private Button btn_send1,btn_send2,btn_send3,btn_send4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_send1 = (Button)findViewById(R.id.button1);
        btn_send2 = (Button)findViewById(R.id.button2);
        btn_send3 = (Button)findViewById(R.id.button3);
        btn_send4 = (Button)findViewById(R.id.button4);

        btn_send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connectToServer();
                    }
                }).start();
            }
        });
        btn_send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        search();
                    }
                }).start();
            }
        });
        btn_send3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mConnection.login("user1", "123456");
                            Log.i(TAG,"    mConnection.login(\"user1\", \"123456\");");
                        } catch (XMPPException e) {
                            e.printStackTrace();
                        } catch (SmackException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        btn_send4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    public void connectToServer()
    {
        Log.i(TAG, "test()");
        try {
            DomainBareJid  xmppServiceDomain  =   (DomainBareJid) JidCreate.from("www.360yule.top");
            XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);     //这里需要添加不适用安全模式，否则会报错没发现ssl
            builder.setXmppDomain(xmppServiceDomain);
            builder.setResource("SmackAndroidTestClient");
            builder.setUsernameAndPassword("t1", "123456");


            mConnection =  new XMPPTCPConnection(builder.build());


            mConnection.addConnectionListener(new ConnectionListener() {
                @Override
                public void authenticated(XMPPConnection connection, boolean resumed) {
                    Log.i(TAG, "authenticated");
                }

                @Override
                public void connected(XMPPConnection connection) {
                    Log.i(TAG, "connected()");
                }

                @Override
                public void connectionClosed() {
                    Log.i(TAG, "connectionClosed()");
                }

                @Override
                public void connectionClosedOnError(Exception e) {
                    Log.i(TAG, "connectionClosedOnError()");
                }

                @Override
                public void reconnectingIn(int seconds) {
                    Log.i(TAG, "reconnectingIn()");
                }

                @Override
                public void reconnectionSuccessful() {
                    Log.i(TAG, "reconnectionSuccessful()");
                }

                @Override
                public void reconnectionFailed(Exception e) {
                    Log.i(TAG, "reconnectionFailed()");
                }

            });

            mConnection.connect();
            mConnection.login();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 注册,需要成功登陆的用户才可以注册成功
     */
    XMPPTCPConnection regConnection;
    public void  reg()
    {
        Log.i(TAG,"reg");
        try {


            DomainBareJid  xmppServiceDomain  =   (DomainBareJid) JidCreate.from("www.360yule.top");
            XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);     //这里需要添加不适用安全模式，否则会报错没发现ssl
            builder.setXmppDomain(xmppServiceDomain);
            builder.setResource("SmackAndroidTestClient");
            builder.setUsernameAndPassword("t1", "123456");

            regConnection =  new XMPPTCPConnection(builder.build());

            regConnection.addConnectionListener(new ConnectionListener() {
                @Override
                public void connected(XMPPConnection connection) {

                }

                @Override
                public void authenticated(XMPPConnection connection, boolean resumed) {
                    reg(regConnection);
                }

                @Override
                public void connectionClosed() {

                }

                @Override
                public void connectionClosedOnError(Exception e) {

                }

                @Override
                public void reconnectionSuccessful() {

                }

                @Override
                public void reconnectingIn(int seconds) {

                }

                @Override
                public void reconnectionFailed(Exception e) {

                }
            });
            regConnection.connect();
            regConnection.login();
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reg(  XMPPTCPConnection  regConnection)
    {
        Log.i(TAG,"reg(  XMPPTCPConnection  regConnection)");
        try{
                    AccountManager accountManager = AccountManager.getInstance(regConnection);
	        	 	accountManager.createAccount(Localpart.from("user6"), "123456");
	         	 	Log.i("Test", "注册成功");
        regConnection.disconnect();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            Log.i("Test","注册失败 "+e.getMessage());
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
    }


    /**
     * 	获取用户的好友信息，需要在用户登录后
     */
    public void roster()
    {
        Log.i("Test"," roster() ");

        Roster roster = Roster.getInstanceFor(mConnection);
        Collection<RosterEntry> entries = roster.getEntries();
        for (RosterEntry entry : entries) {
            Log.i("Test",""+entry);
        }
        /**
         * 添加 好友信息监听器
         */
        roster.addRosterListener(new RosterListener() {
            @Override
            public void entriesAdded(Collection<Jid> addresses) {
                Log.i("Test", "entriesAdded" + addresses.toString());
            }

            @Override
            public void entriesUpdated(Collection<Jid> addresses) {
                Log.i("Test", "entriesUpdated" + addresses.toString());
            }

            @Override
            public void entriesDeleted(Collection<Jid> addresses) {
                Log.i("Test", "entriesDeleted" + addresses.toString());
            }

            @Override
            public void presenceChanged(Presence presence) {
                Log.i("Test", "presenceChanged" + presence.toString());
            }

        });
    }

    /**
     * 搜索用户
     */
    public void search()
    {
        Log.i("Test","  search() ");
        try {
            UserSearchManager search = new UserSearchManager(mConnection);

            //注意这里服务器的写法。不一定是 “serach.域名” ，所以一定要是使用mConnection.getXMPPServiceDomain() 获取他的domain
            DomainBareJid  xmppServiceDomain  =   (DomainBareJid) JidCreate.from("search."+mConnection.getXMPPServiceDomain());
            Log.i(TAG,"domain"+mConnection.getXMPPServiceDomain());
            Form  searchForm = search .getSearchForm( xmppServiceDomain);
            Form answerForm = searchForm.createAnswerForm();
            answerForm.setAnswer("Username", true);
            answerForm.setAnswer("search","user");
//        ReportedData data = search.getSearchResults(answerForm, "search." + mConnection.getServiceName());
            ReportedData data = search.getSearchResults(answerForm, xmppServiceDomain);
            if (data.getRows() != null) {
                for (ReportedData.Row row: data.getRows()) {
                    for (String value: row.getValues("jid")) {
                        Log.i("Test"," " + value);
                    }
                }

            }
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }


    }
}
