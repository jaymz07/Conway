import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Conway extends Applet implements MouseListener, MouseMotionListener, KeyListener
{
	int XBOUND, YBOUND;

	boolean init=true;

	BufferedImage image;

	Graphics iPage,page;

	Point mP=new Point(0,0),mPP=new Point(0,0);
	boolean [][] vals;

	public void paint(Graphics p)
	{
		if(init)
		{
			Dimension appletSize = this.getSize();
  	 		YBOUND = appletSize.height;
   			XBOUND = appletSize.width;

   			vals=new boolean[YBOUND][XBOUND];

			random(0.24);
			//checkered(1.01,3);

	   		image = new BufferedImage(XBOUND,YBOUND,BufferedImage.TYPE_3BYTE_BGR);

			init=false;
			this.addKeyListener(this);
			this.addMouseListener( this );
	 		this.addMouseMotionListener( this );

		}

		image = new BufferedImage(XBOUND,YBOUND,BufferedImage.TYPE_3BYTE_BGR);
		iPage=image.getGraphics();

		generateImage();


		p.drawImage(image,0,0,new Color(255,255,255),null);

		conwayIterate();

		repaint();

	}

	public void stop()
	{
	}
	public int random(double density)
	{
		int out=0;
		for(int i=0;i<YBOUND;i++)
			  for(int j=0;j<XBOUND;j++)
			      if(Math.random()<=0.24) {
					vals[i][j]=true;
					out++;
			      }
		return out;
	}
	public int checkered(double spacingX,double spacingY)
	{
		int out=0;
		for(int i=0;i<YBOUND;i++)
			  for(int j=0;j<XBOUND;j++)
			      if((double)i%spacingY<spacingY/10&&(double)j%spacingX<spacingX/10) {
					vals[i][j]=true;
					out++;
			      }
		return out;
	}
	public int grid(double spacingX,double spacingY)
	{
		int out=0;
		for(int i=0;i<YBOUND;i++)
			  for(int j=0;j<XBOUND;j++)
			      if((double)i%spacingY<spacingY/2&&(double)j%spacingX<spacingX/2) {
					vals[i][j]=true;
					out++;
			      }
		return out;
	}
	public void generateImage()
	{
	    iPage.setColor(Color.BLACK);
	    iPage.fillRect(0,0,XBOUND,YBOUND);
	    iPage.setColor(Color.WHITE);
	    for(int i=0;i<XBOUND;i++)
	      for(int j=0;j<YBOUND;j++)
		if(vals[j][i])
		  iPage.fillRect(i,j,1,1);
	}
	public void conwayIterate()
	{
	 boolean [][] out = new boolean[YBOUND][XBOUND];
	 for(int i=0;i<YBOUND;i++)
	  for(int j=0;j<XBOUND;j++)
	    {
	      int nb=getNumNeighbors(i,j);
	      out[i][j]=vals[i][j]&&(nb==2||nb==3)||(!vals[i][j]&&nb==3);
	    }
	 for(int i=0;i<YBOUND;i++)
	  for(int j=0;j<XBOUND;j++)
	    {
	      vals[i][j]=out[i][j];
	    }

	}
	public int getNumNeighbors(int i, int j)
	{
	  int out=0;
	  if(i>0)
	  {
	    if(j>0&&vals[i-1][j-1])
	      out++;
	    if(j<vals[0].length-1&&vals[i-1][j+1])
	      out++;
	    if(vals[i-1][j])
	      out++;
	  }
	  if(i<vals.length-1)
	  {
	    if(j>0&&vals[i+1][j-1])
	      out++;
	    if(j<vals[0].length-1&&vals[i+1][j+1])
	      out++;
	    if(vals[i+1][j])
	      out++;
	  }
	  if(j>0&&vals[i][j-1])
	    out++;
	  if(j<vals[0].length-1&&vals[i][j+1])
	    out++;
	  return out;
	}

   public void keyPressed(KeyEvent e) {

   		if(e.getKeyCode()==KeyEvent.VK_R)
   		{
   			random(0.25);
   		}
   		if(e.getKeyCode()==KeyEvent.VK_1)
   		{
   			clear();
   			checkered(1,2);
   		}
   		if(e.getKeyCode()==KeyEvent.VK_2)
   		{
   			clear();
   			checkered(1,3);
   		}
   		if(e.getKeyCode()==KeyEvent.VK_3)
   		{
   			clear();
   			checkered(1.01,3);
   		}
   		if(e.getKeyCode()==KeyEvent.VK_4)
   		{
   			clear();
   			checkered(1,2.1);
   		}
   		if(e.getKeyCode()==KeyEvent.VK_5)
   		{
   			clear();
   			checkered(1,2.01);
   		}
   		if(e.getKeyCode()==KeyEvent.VK_6)
   		{
   			clear();
   			grid(200,200);
   		}
   		repaint();

	}

	public void keyReleased(KeyEvent e) {

  	}

	public void keyTyped(KeyEvent e) {

	}


	public void update( Graphics g ) {
		paint(g);
   }

	public void mouseEntered( MouseEvent e ) {
      // called when the pointer enters the applet's rectangular area
   }
   public void mouseExited( MouseEvent e ) {
      // called when the pointer leaves the applet's rectangular area
   }
   public void mouseClicked( MouseEvent e ) {
      int j=e.getX(),i=e.getY(),k=(int)(Math.random()*4);
      if(i>0&&i<vals.length-1&&j>0&&j<vals[0].length-1)
      {
	vals[i][j]=true;
	if(k==0) {
	 vals[i-1][j-1]=true;
	 vals[i-1][j]=true;
	 vals[i][j+1]=true;
	 vals[i+1][j-1]=true;
	}
	if(k==1) {
	 vals[i-1][j+1]=true;
	 vals[i-1][j]=true;
	 vals[i][j-1]=true;
	 vals[i+1][j+1]=true;
	}
	if(k==2) {
	 vals[i+1][j-1]=true;
	 vals[i+1][j]=true;
	 vals[i][j+1]=true;
	 vals[i-1][j-1]=true;
	}
	if(k==3) {
	 vals[i+1][j+1]=true;
	 vals[i+1][j]=true;
	 vals[i][j-1]=true;
	 vals[i-1][j+1]=true;
	}
      }
   }
   public void mousePressed( MouseEvent e ) {  // called after a button is pressed down

   }
   public void mouseReleased( MouseEvent e ) {  // called after a button is released

	}
   public void mouseMoved( MouseEvent e ) {  // called during motion when no buttons are down

		mPP=mP;
		mP=new Point(e.getX(),e.getY());

   }
   public void mouseDragged( MouseEvent e ) {  // called during motion with buttons down
   	mPP=mP;
	mP=new Point(e.getX(),e.getY());
	vals[e.getY()][e.getX()]=true;

   }
   public void clear()
   {
   	for(int i=0;i<YBOUND;i++)
   		for(int j=0;j<XBOUND;j++)
   			vals[i][j]=false;
   }
}