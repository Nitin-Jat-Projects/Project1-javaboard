import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;


class SendToServer extends Thread
{
	private aaa a;
	private static Queue<Integer> q=new LinkedList<>();
	
	public  SendToServer(aaa a)
	{
		this.a=a;
		this.start();
	}
	
	        public void run()
		{
			
		while(true)
		{
		String response="Ram";
		try
		{
			
		String server="localhost";
	        int portNumber=14451;
	        Socket socket=new Socket(server,portNumber);
		OutputStream os;
		OutputStreamWriter osw;
		os=socket.getOutputStream();
		osw=new OutputStreamWriter(os);
		String request="recieve"+","+"hello"+"#";
		osw.write(request);
		System.out.println("Request send : " + request);
		osw.flush();

                String work="hello";
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
		if(response.equals("false"))
		{
		        socket.close();	
			//continue;
		}
		else
		{
                String splits[]=response.split(",");
                work=splits[0];
		int lastX=Integer.parseInt(splits[1]);
		int lastY=Integer.parseInt(splits[2]);
		int x=Integer.parseInt(splits[3]);
		int y=Integer.parseInt(splits[4]);
		System.out.println(work +","+lastX+","+lastY+","+x+","+y);
		q.add(lastX);
		q.add(lastY);
		q.add(x);
		q.add(y);
                if(work.equals("clearCanvas"))
                {
                    
                    a.repaint();
                }
                if(work.equals("draw"))
                {
                 aaa.isErase=false;
		awt.a.draw(lastX,lastY,x,y);
                }
                if(work.equals("erase"))
                {
                  aaa.isErase=true;
                  awt.a.draw(lastX,lastY,x,y);
                }
		socket.close();
		}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
                
		}
		
		
	}
		}




class aaa extends Canvas
{
	
	Graphics g;
public static boolean isErase=false;
        
       public aaa()
       {
	       setBackground(new Color(0,0,0));
	       setForeground(Color.white);
       }
       public  boolean draw(int lastX,int lastY,int x,int y)
	{
		
	    	g=getGraphics();
if(isErase)
{
repaint(lastX,lastY,x,y);
}               
                 else
                {
		g.drawLine(lastX,lastY,x,y);
                 }		
		return true;
	}
      

}
class awt extends Frame
{
	public static aaa a;
	public awt()
	{
		a=new aaa();
		setLayout(new BorderLayout());
Panel p=new Panel();
p.setLayout(new GridLayout(10,3));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));
p.add(new Label(" "));

		add(a,BorderLayout.CENTER);
add(p,BorderLayout.EAST);
		setLocation(100,100);
addWindowListener(new WindowAdapter()
{
public void windowClosing(WindowEvent e)
{
System.exit(0);
}
});
		setSize(600,600);
		setVisible(true);
		new SendToServer(a);
	}
}
class psp
{
	public static void main(String args[])
	{
		awt a=new awt();
	}
}
