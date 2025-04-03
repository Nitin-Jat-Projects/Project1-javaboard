import java.io.*;
import java.net.*;
import java.util.*;


class RequestProccessor extends Thread
{
	public static 	Queue<String> q=new LinkedList<>();
	
	private Socket socket;

	public RequestProccessor(Socket socket)
	{
		
		this.socket=socket;
		this.start();

	}
	public void run()
{
		try
		{
			InputStream is;
			InputStreamReader isr;
			OutputStream os;
			OutputStreamWriter osw;
			StringBuffer sb;
			
			String request;
			String response;
			int oneByte;

                        is=this.socket.getInputStream();
			isr=new InputStreamReader(is);
			sb=new StringBuffer();
			while(true)
			{
			       oneByte=isr.read();
			       if(oneByte==-1) break;
			       if(oneByte=='#') break;
			       sb.append((char)oneByte);
			}

			request=sb.toString();
			System.out.println("Request arrived : " +request);
			String splits[]=request.split(",");
			String command=splits[0];
                        String work=splits[1];
			if(command.equals("send"))
			{
                        if(work.equals("draw"))
                        {
			int lastX=Integer.parseInt(splits[2]);
			int lastY=Integer.parseInt(splits[3]);
			int x=Integer.parseInt(splits[4]);
			int y=Integer.parseInt(splits[5]);
                       String addString="draw"+","+lastX+","+lastY+","+x+","+y+"#";
		       q.add(addString);
		       
			os=this.socket.getOutputStream();
			osw=new OutputStreamWriter(os);
			response="true draw#";
			System.out.println("Response send to send: " + response);
			osw.write(response);
			osw.flush();
			this.socket.close();
                        }
                        if(work.equals("erase"))
                        {
                         int lastX=Integer.parseInt(splits[2]);
			int lastY=Integer.parseInt(splits[3]);
			int x=Integer.parseInt(splits[4]);
			int y=Integer.parseInt(splits[5]);
                       String addString="erase"+","+lastX+","+lastY+","+x+","+y+"#";
		       q.add(addString);
		       
			os=this.socket.getOutputStream();
			osw=new OutputStreamWriter(os);
			response="true Erased#";
			System.out.println("Response send to send: " + response);
			osw.write(response);
			osw.flush();
			this.socket.close();


                        }
                        if(work.equals("clearCanvas"))
                        {
                         int lastX=Integer.parseInt(splits[2]);
			int lastY=Integer.parseInt(splits[3]);
			int x=Integer.parseInt(splits[4]);
			int y=Integer.parseInt(splits[5]);
                       String addString="clearCanvas"+","+lastX+","+lastY+","+x+","+y+"#";
		       q.add(addString);
		       
			os=this.socket.getOutputStream();
			osw=new OutputStreamWriter(os);
			response="true clearCanvas#";
			System.out.println("Response send to send: " + response);
			osw.write(response);
			osw.flush();
			this.socket.close();


                        }



			}


			if(command.equals("recieve"))
			{       
				System.out.println("recieve "+ q.size());
				if((q.size()>0))
				{
				String nitin=q.remove();					
		response=nitin+"#";
		/*try
		{
		Thread.sleep(5);
		}catch(Exception e)
		{
		}*/
				}
				else
				{
					response="false#";
					/*try
					{					Thread.sleep(5);
					}catch(Exception e)
					{

					}*/
				}
		os=this.socket.getOutputStream();
			osw=new OutputStreamWriter(os);
			osw.write(response);
			System.out.println("Response send : " + response);

			osw.flush();
			this.socket.close();
			}

		}catch(Exception exception)
		{

		}


	}


}
class MultithreadedServer
{
	private ServerSocket serverSocket;
	private int portNumber ;
	public MultithreadedServer(int portNumber)
	{
		this.portNumber=portNumber;
		try
		{
			this.serverSocket=new ServerSocket(this.portNumber);

		}catch(Exception exception)
		{
			System.out.println(exception.getMessage());
		}
	}
	public void startServer()
	{
		try
		{
			Socket socket;
			while(true)
			{
				socket=this.serverSocket.accept();
				new RequestProccessor(socket);
			}

		}catch(Exception exception)
		{
			System.out.println(exception.getMessage());
		}

	}

	public static void main(String args[])
	{
		try
		{
		MultithreadedServer mts;
		mts=new MultithreadedServer(14451);
		mts.startServer();
		}catch(Exception exception)
		{
			System.out.println(exception.getMessage());
		}
	}
}
