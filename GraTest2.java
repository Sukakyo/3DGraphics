package graphics3d;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GraTest2 extends JPanel {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  
  ArrayList<Polygon> figs = new ArrayList<Polygon>();
  
  final Ray ray = new Ray(0,1,0);
  //Ray ray = new Ray(0,1,0);
  Observer ob = new Observer(0,0,300, new Vector3d(0,1,-0.3) );
  //Vector3d v1 = new Vector3d(1,2,3);
  
  
  double d = 50;
  double dist = 1000;
  Vector3d first_center = new Vector3d(d*Math.sqrt(3),0,0);
  Vector3d center = new Vector3d(d*Math.sqrt(3),0,0);
  
  
  Vector3d[] first = {new Vector3d(0,dist,d*2),
		  new Vector3d(-center.getXpram(),dist-center.getYpram(),-d),
          new Vector3d(center.getXpram(),dist+center.getYpram(),-d)};
  Vertex point1_0 = new Vertex(Color.BLUE,Vector3d.copyVec(first[0]));
  Vertex point1_1 = new Vertex(Color.BLUE,Vector3d.copyVec(first[1]));
  Vertex point1_2 = new Vertex(Color.BLUE,Vector3d.copyVec(first[2]));

  Polygon p1 = new Polygon(Color.BLUE,ray,point1_0,point1_1,point1_2);
  
  
  double dist2 = 1200;
  Vector3d[] first2 = {new Vector3d(0,dist2,d*2),
		  new Vector3d(-center.getXpram(),dist2-center.getYpram(),-d),
          new Vector3d(center.getXpram(),dist2+center.getYpram(),-d)};
  Vertex point2_0 = new Vertex(Color.RED,Vector3d.copyVec(first2[0]));
  Vertex point2_1 = new Vertex(Color.RED,Vector3d.copyVec(first2[1]));
  Vertex point2_2 = new Vertex(Color.RED,Vector3d.copyVec(first2[2]));

  Polygon p2 = new Polygon(Color.RED,ray,point2_0,point2_1,point2_2);
  
  
  double dist3 = 800;
  Vector3d[] first3 = {new Vector3d(0,dist3,d*2),
		  new Vector3d(-center.getXpram(),dist3-center.getYpram(),-d),
          new Vector3d(center.getXpram(),dist3+center.getYpram(),-d)};
  Vertex point3_0 = new Vertex(Color.GREEN,Vector3d.copyVec(first3[0]));
  Vertex point3_1 = new Vertex(Color.GREEN,Vector3d.copyVec(first3[1]));
  Vertex point3_2 = new Vertex(Color.GREEN,Vector3d.copyVec(first3[2]));

  Polygon p3 = new Polygon(Color.GREEN,ray,point3_0,point3_1,point3_2);

  
  

  
  
  
  
  

  public GraTest2(){
    setOpaque(false);
    final long tm0 = System.currentTimeMillis();
    /*Vector3d[] newPos = new Vector3d[3];
    for(int i = 0;i <3;i++) {
      newPos[i]= new Vector3d(first[i].getXpram(),first[i].getYpram(),first[i].getZpram());
    }
    */
    
    figs.add(p2);
    figs.add(p3);
    figs.add(p1);
    
    
    new javax.swing.Timer(30, new ActionListener() {
    	public void actionPerformed(ActionEvent evt) {
    		double tm = 0.001 * (System.currentTimeMillis()-tm0);
    		/*
    		for(int i = 0; i< 3;i++) {
    				newPos[i].setXpram(first[i].getXpram());
    				newPos[i].setYpram(first[i].getYpram());
    			    newPos[i].setZpram(first[i].getZpram());
    			    
    			    
    		}
    		p1.setPos(newPos[0],newPos[1],newPos[2]);
    		*/
    		
    		Vector3d buf = Vector3d.copyVec(first_center);
    		buf.horizontal_turn(Math.PI*tm);
    		Vector3dOpe.assign(center,buf);
    		//System.out.println(center.getXpram()+" "+center.getYpram()+" "+center.getZpram());
    		first[0].setPram(0,dist,d*2);
            first[1].setPram(-center.getXpram(),dist-center.getYpram(),-d);
    		first[2].setPram(center.getXpram(),dist+center.getYpram(),-d);
    		//System.out.println(center.getXpram()+" "+center.getYpram());
    		point1_0.setVec(Vector3d.copyVec(first[0]));
    		point1_1.setVec(Vector3d.copyVec(first[1]));
    		point1_2.setVec(Vector3d.copyVec(first[2]));
    		p1.setPolygon(ray,point1_0 , point1_1, point1_2);
    		
    		first2[0].setPram(0,dist2,d*2);
            first2[1].setPram(-center.getXpram(),dist2-center.getYpram(),-d);
    		first2[2].setPram(center.getXpram(),dist2+center.getYpram(),-d);
    		//System.out.println(center.getXpram()+" "+center.getYpram());
    		point2_0.setVec(Vector3d.copyVec(first2[0]));
    		point2_1.setVec(Vector3d.copyVec(first2[1]));
    		point2_2.setVec(Vector3d.copyVec(first2[2]));
    		p2.setPolygon(ray,point2_0 , point2_1, point2_2);
    		
            
    		repaint();
    	}
    }).start();
    
    //repaint();
  }

  public void paintComponent(Graphics g){
	  Polygon[] array = figs.toArray(new Polygon[figs.size()]);
	  
	  Polygon[] sp = new Polygon[figs.size()];
	  
      sp = SortPolygon.sort(ray, array, ob);
	  
     
    for(Polygon f:sp) {
        System.out.println(f.getVec(1).getYpram());
    	f.draw(g, ob);
    }
    
  }


  public static void main(String[] args) {
    JFrame app = new JFrame();
    app.add(new GraTest2());
    app.setSize(500,500);
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    app.setVisible(true);
  }
}



