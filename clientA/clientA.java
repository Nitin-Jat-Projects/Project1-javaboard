import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
class SendToServer 
{
	public static void sendToServer(String work,int lastX,int lastY ,int x ,int y)
	{
		String response="false";
		try
		{
			
		String server="localhost";
	        int portNumber=14451;		
	        Socket socket=new Socket(server,portNumber);
		OutputStream os;
		OutputStreamWriter osw;
		os=socket.getOutputStream();
		osw=new OutputStreamWriter(os);
		String request="send"+","+work+","+lastX+","+lastY+","+x+","+y+"#";
		osw.write(request);
		System.out.println("Request send : " + request);
		osw.flush();


		InputStream is;
		InputStreamReader isr;
		StringBuffer sb;
		is=socket.getInputStream();
		isr=new InputStreamReader(is);
		sb=new StringBuffer();
		int oneByte;

                while(true)
		{
			oneByte=isr.read();
			if(oneByte==-1) break;
			if(oneByte=='#') break;
			sb.append((char)oneByte);

		}
		 response=sb.toString();
		System.out.println("Response Arrived : " + response);
                
		socket.close();
                
		}catch(Exception exception)
		{
			System.out.println(exception.getMessage());
		}
		
	}
}

class MyDrawingPad extends Canvas
{
        public int radius=20;
	String response;
	private int lastX,lastY;
private Graphics g;
public static boolean isErase=false;
	public MyDrawingPad()
	{
		setBackground(new Color(255,255,255));
		setForeground(Color.black);
	}
	public boolean mouseDown(Event e,int x,int y)
	{
        
		lastX=x;
		lastY=y;
		return true;
	}
	public boolean mouseDrag(Event e,int x,int y)
	{
		g=getGraphics();
if(isErase)
{
g.drawRoundRect(x,y,radius,radius,radius,radius);
repaint(x,y,2*radius,2*radius);
SendToServer.sendToServer("erase",x,y,2*radius,2*radius);
}
                else
                {
                g.drawLine(lastX,lastY,x,y);
		SendToServer.sendToServer("draw",lastX,lastY,x,y);
                 }	
		lastX=x;
		lastY=y;
		return true;
	}
	
}
class Client2 extends Frame
{
	private MyDrawingPad mpd;
	public Client2()
	{
		mpd=new MyDrawingPad();
		setLayout(new BorderLayout());
Panel p=new Panel();
               p.setLayout(new GridLayout(10,3));
               p.add(new Label(" "));
               Button b=new Button("clear");
               p.add(b);
               p.add(new Label(" "));
p.add(new Label(" "));
Button c=new Button("erase");
p.add(c);
p.add(new Label(" "));




p.add(new Label("size"));
TextField t=new TextField(2);
p.add(t);
p.add(new Label(" "));

p.add(new Label(" "));
Button d=new Button("Pen");
p.add(d);
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));p.add(new Label(" "));

                 add(p,BorderLayout.EAST);

		add(mpd,BorderLayout.CENTER);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev)
			{
				System.exit(0);
			}
		});
b.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
MyDrawingPad.isErase=false;
mpd.repaint();
SendToServer.sendToServer("clearCanvas",-1,-1,-1,-1);
}
});

d.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
MyDrawingPad.isErase=false;
}
});


c.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{

mpd.radius=Integer.parseInt(t.getText());
MyDrawingPad.isErase=true;
}
});


		setLocation(100,100);
		setSize(600,600);
		setVisible(true);
	}
	public static void main(String args[])
	{
		Client2 c=new Client2();
	

	}
}
