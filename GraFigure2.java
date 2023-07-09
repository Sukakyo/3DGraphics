package graphics3d;

import java.awt.*;
//import javax.swing.*;



interface Figure {
  final double centDis = 1000;
	
  public void draw(Graphics g, Observer ob);
}

class Vertex {
	Vector3d point;
	Color color;
	
	public Vertex(Color c,Vector3d v) {
		setColor(color);
		setVec(Vector3d.copyVec(v));
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public void setVec(Vector3d v) {
		this.point = v;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public Vector3d getVec() {
		return this.point;
	}
	
	public Vector3d getVecValue() {
		return Vector3d.copyVec(this.point);
	}
}

class Polygon implements Figure{
  Color col;
  Vertex[] pos=new Vertex[3];
  Vector3d nVec;
  double rate;

  public Polygon(Color c,Ray r,Vertex p0,Vertex p1,Vertex p2){
    this.pos[0]=new Vertex(p0.getColor(),new Vector3d(0,0,0)); 
    this.pos[1]=new Vertex(p1.getColor(),new Vector3d(0,0,0)); 
    this.pos[2]=new Vertex(p2.getColor(),new Vector3d(0,0,0)); 
    this.nVec=new Vector3d(0,0,0);
    
    this.setColor(c);
    this.setPolygon(r, p0, p1, p2);
    //this.setBright(r);
  }
  
  public void move(double x,double y,double z) {
	  for(int i = 0;i < 3;i++) {
	      this.pos[i].getVec().move(x, y, z);
	  }
  }
  
  public void setColor(Color c) {
	  this.col = c;
  }
  
  public void setPolygon(Ray r,Vertex p0, Vertex p1, Vertex p2) {
	  this.setPos(p0.getVec(), p1.getVec(), p2.getVec());
	  this.setNvec(this.pos[0].getVec(),this.pos[1].getVec() ,this.pos[2].getVec() );
	  this.setBright(r);
  }
  
  public void setPos(Vector3d p0,Vector3d p1,Vector3d p2) {
	  this.pos[0].getVec().setVec(Vector3d.copyVec(p0));
	  this.pos[1].getVec().setVec(Vector3d.copyVec(p1));
	  this.pos[2].getVec().setVec(Vector3d.copyVec(p2));
	  
  }
  
  public void setNvec(Vector3d p0,Vector3d p1,Vector3d p2) {
	    Vector3d v1 = Vector3dOpe.subtraction(Vector3d.copyVec(p2), Vector3d.copyVec(p0));
	    Vector3d v2 = Vector3dOpe.subtraction(Vector3d.copyVec(p1), Vector3d.copyVec(p0));

	    this.nVec = Vector3dOpe.crossPro(v1, v2);
  }
  
  public void setBright(Ray r) {
	    
	    //System.out.println(this.nVec.getXpram()+" "+this.nVec.getYpram()+" "+this.nVec.getZpram());
	    rate = Vector3dOpe.angleCos(r.getRayBeam(), this.nVec); 
	    if(rate<0) {rate=-rate;}
	    //System.out.println(rate);
  }
  
  public Vector3d getVec(int i) {
	  return Vector3d.copyVec(this.pos[i].getVec());
  }
  
  public Vertex getVertex(int i) {
	  return this.pos[i];
  }
  
  public Vertex getVertexValue(int i) {
	  Vertex p = new Vertex(this.col,Vector3d.copyVec(this.getVec(i)));
	  return p;
  }
  
  public double getPersComponent(Observer ob) {
	  double[] cosAngle = new double[3];
	  double[] persLength = new double[3];
	  
	  Vector3d[] relPos = new Vector3d[3];
	  
	  for(int i = 0; i < 3; i++) {
		  relPos[i] = Vector3dOpe.subtraction(this.pos[i].getVec(), ob.pos);
	  }
	  
	  for(int i = 0; i < 3; i++) {
	      cosAngle[i] = Vector3dOpe.angleCos(relPos[i], ob.pers);
	      persLength[i] = Vector3dOpe.norm(relPos[i]) * cosAngle[i];
	  }
	  
	  if(persLength[1] > persLength[0]) {
		  if(persLength[2]>persLength[1]) {
			  return persLength[2];
		  }
		  else {
			  return persLength[1];
		  }
	  }else {
		  if(persLength[2]>persLength[0]) {
			  return persLength[2];
		  }
		  else {
			  return persLength[0];
		  }
	  }
  }
  
  public Vector3d getNorVec(){
	  return Vector3d.copyVec(this.nVec);
  }
  
  /*public Vector3d[] getPos() {
	  return this.pos;
  }*/
  
  public Color getColor() {
	  return this.col;
  }
  

  public void draw(Graphics g, Observer ob){
	  /* 描画する点のベクトル */
	  Vector3d vec;
	  double angleCos;
	  double angleSin;
	  double norm;
	  Vector3d[] graphicVec = new Vector3d[3];
	  
	  /* 描画用の平面ベクトル */
	  double[] x= new double[3];
	  double[] y= new double[3];
	  int[] xpos = new int[3];
	  int[] ypos = new int[3];
	  
	  
	  norm = Vector3dOpe.norm(ob.pers);
	  Vector3d pVec = Vector3dOpe.scholarMulti(centDis/norm,ob.pers);
	  
	  
	  vec = new Vector3d(0,0,0);
	  for(int i = 0; i < 3; i++) {
		  //観測者から見たオブジェクトの位置ベクトル(以降位置ベクトルとする)を計算
	    Vector3dOpe.assign(vec,Vector3dOpe.subtraction(this.pos[i].getVec(), ob.pos));
	      //視点ベクトルと位置ベクトルのなす角
	    angleCos = Vector3dOpe.angleCos(Vector3d.copyVec(vec), ob.pers);
	    if(angleCos < 0 ) {
	    	angleCos = -angleCos;
	    }
	      //位置ベクトルの大きさ
	    norm = Vector3dOpe.norm(Vector3d.copyVec(vec));
	      //視界平面上に投影するベクトル
	    graphicVec[i] = Vector3dOpe.scholarMulti(((centDis/angleCos)/norm),Vector3d.copyVec(vec));
	    if(i == 2) {
	    	//System.out.println(graphicVec[i].getXpram()+" "+
	    	//	           graphicVec[i].getYpram()+" "+
	    	//	           graphicVec[i].getZpram());
	    }
	    
	      //視界平面上の位置ベクトル
	    Vector3dOpe.assign(vec,Vector3dOpe.subtraction(graphicVec[i], pVec));
	      //それぞれ水平成分、垂直成分の割合
	    angleCos = Vector3dOpe.angleCos(ob.basis,Vector3d.copyVec(vec));
	    angleSin = Vector3dOpe.angleSin(ob.basis,Vector3d.copyVec(vec),ob.pers);
	    
	      //視界平面上の座標
	    x[i]=Vector3dOpe.norm(Vector3d.copyVec(vec))*angleCos;
	    y[i]=Vector3dOpe.norm(Vector3d.copyVec(vec))*angleSin;
	      //縮尺合わせ
	    xpos[i]=-(int)(x[i]*500/centDis)+250;
	    ypos[i]=-(int)(y[i]*500/centDis)+250;
	    xpos[i]=xpos[i];
	    ypos[i]=ypos[i];
	  }
	  
	  
  	  //System.out.println(graphicVec[1].getXpram()+" "+
	  //         graphicVec[1].getYpram()+" "+
	  //  graphicVec[1].getZpram());
	  
	  
	  //視点ベクトルと視界平面への投影ベクトルの内積(これが負のポリゴンは描画しない)
	  double[] dot = new double[3];
	  for(int i=0;i<3;i++) {
		  dot[i]=Vector3dOpe.dotPro(graphicVec[i], ob.pers);
	  }
	  
	  //System.out.println(dot[0]+" "+dot[1]+" "+dot[2]);
	  //System.out.println(dot[1]);
	  
	    int red = (int)(this.col.getRed()*rate);
	    int green = (int)(this.col.getGreen()*rate);
	    int blue = (int)(this.col.getBlue()*rate);
	    
	    //System.out.println(red+" "+green+" "+blue);
		  
		Color realCol = new Color(red,green,blue);
	  
	  //任意の内積が正のポリゴンを描画
	  if(dot[0]>0 && dot[1]>0 && dot[2]>0) {
	    g.setColor(realCol);
	    g.fillPolygon(xpos, ypos, 3);
	  }
	  
  }
}


class SortPolygon {
	
	public static Polygon[] sort(Ray r,Polygon[] pol,Observer ob) {
		Polygon[] newP = new Polygon[pol.length];
		int i = 0;
		int j = 0;
		int k = 0;
		Polygon pivot;
		
		
		  for(i=0;i<pol.length;i++) {
			  newP[i]=new Polygon(pol[0].getColor(),
					  r,
					  pol[0].getVertexValue(0),
					  pol[0].getVertexValue(1),
					  pol[0].getVertexValue(2));
		  }
		
		if(pol.length>2) {
			
			pivot = new Polygon(pol[0].getColor(),
                    r,
                    pol[0].getVertexValue(0),
                    pol[0].getVertexValue(1),
                    pol[0].getVertexValue(2));
		
		  for(i=1;i<pol.length;i++) {
			  if(pol[i].getPersComponent(ob)>pivot.getPersComponent(ob)) {
				  newP[j].setColor(pol[i].getColor());
				  newP[j].getVertex(0).setVec(pol[i].getVec(0));
				  newP[j].getVertex(1).setVec(pol[i].getVec(1));
				  newP[j].getVertex(2).setVec(pol[i].getVec(2));
				  j++;
			  }else {
				  newP[(pol.length-1)-k].setColor(pol[i].getColor());
				  newP[(pol.length-1)-k].getVertex(0).setVec(pol[i].getVec(0));
				  newP[(pol.length-1)-k].getVertex(1).setVec(pol[i].getVec(1));
				  newP[(pol.length-1)-k].getVertex(2).setVec(pol[i].getVec(2));
				  k++;
			  }
		  }
		  
		  Polygon[] largeP = new Polygon[j];
		  Polygon[] smallP = new Polygon[k];
		  for(i=0;i<j;i++) {
			  largeP[i]=new Polygon(pol[0].getColor(),
					  r,
					  pol[0].getVertexValue(0),
					  pol[0].getVertexValue(1),
					  pol[0].getVertexValue(2));
		  }
		  for(i=0;i<k;i++) {
			  smallP[i]=new Polygon(pol[0].getColor(),
					  r,
					  pol[0].getVertexValue(0),
					  pol[0].getVertexValue(1),
					  pol[0].getVertexValue(2));
		  }
		  for(i=0;i<j;i++) {
			  largeP[i].setColor(newP[i].getColor());
			  largeP[i].setPolygon(r, newP[i].getVertexValue(0), 
		              newP[i].getVertexValue(1), 
		              newP[i].getVertexValue(2));
		  }
		  for(i=0;i<k;i++) {
			  smallP[i].setColor(newP[(newP.length-1)-i].getColor());
			  smallP[i].setPolygon(r, newP[(newP.length-1)-i].getVertexValue(0), 
					  newP[(newP.length-1)-i].getVertexValue(1), 
					  newP[(newP.length-1)-i].getVertexValue(2));
		  }
		  Polygon[] sortL = sort(r,largeP,ob);
		  Polygon[] sortS = sort(r,smallP,ob);
		
		  for(i=0;i<j;i++) {
			  largeP[i].setColor(sortL[i].getColor());
			  largeP[i].setPolygon(r, sortL[i].getVertexValue(0), 
			              sortL[i].getVertexValue(1), 
			              sortL[i].getVertexValue(2));
		  }
		  for(i=0;i<k;i++) {
			  smallP[i].setColor(sortS[i].getColor());
			  smallP[i].setPolygon(r, sortS[i].getVertexValue(0), 
			                          sortS[i].getVertexValue(1), 
			                          sortS[i].getVertexValue(2));
		  }
		
		  for(i=0;i<newP.length;i++) {
			  if(i<j) {
				  newP[i].setColor(largeP[i].getColor());
				  newP[i].setPolygon(r, largeP[i].getVertexValue(0), 
				              largeP[i].getVertexValue(1), 
				              largeP[i].getVertexValue(2));
			  }else if(i==j) {
				  newP[i].setColor(pivot.getColor());
					newP[i].setPolygon(r, pivot.getVertexValue(0), 
				              pivot.getVertexValue(1), 
				              pivot.getVertexValue(2));
			  }else {
				  newP[i].setColor(smallP[i-j-1].getColor());
					newP[i].setPolygon(r, smallP[i-j-1].getVertexValue(0), 
				              smallP[i-j-1].getVertexValue(1), 
				              smallP[i-j-1].getVertexValue(2));
			  }
		  }
		  
		  return newP;
		}else if(pol.length > 1){
			if(pol[0].getPersComponent(ob)>pol[1].getPersComponent(ob)) {
				newP[0].setColor(pol[0].getColor());
				newP[0].setPolygon(r, pol[0].getVertexValue(0), 
						              pol[0].getVertexValue(1), 
						              pol[0].getVertexValue(2));
				newP[1].setColor(pol[1].getColor());
				newP[1].setPolygon(r, pol[1].getVertexValue(0), 
			                          pol[1].getVertexValue(1), 
			                          pol[1].getVertexValue(2));
			}else {
				newP[1].setColor(pol[0].getColor());
				newP[1].setPolygon(r, pol[0].getVertexValue(0), 
			                          pol[0].getVertexValue(1), 
			                          pol[0].getVertexValue(2));
				newP[0].setColor(pol[1].getColor());
				newP[0].setPolygon(r, pol[1].getVertexValue(0), 
			                          pol[1].getVertexValue(1), 
			                          pol[1].getVertexValue(2));
			}
			return newP;
		}else if(pol.length > 0){
			
			pivot = new Polygon(pol[0].getColor(),
                    r,
                    pol[0].getVertexValue(0),
                    pol[0].getVertexValue(1),
                    pol[0].getVertexValue(2));
			
			newP[0].setColor(pivot.getColor());
			newP[0].setPolygon(r, pivot.getVertexValue(0), 
		              pivot.getVertexValue(1), 
		              pivot.getVertexValue(2));

		  return newP;
		}
		
		return newP;
	}
}

class Tetrahedron implements Figure{
	Color col;
	Vertex[] pos = new Vertex[4];
	Polygon[] pol = new Polygon[4];
	
	public Tetrahedron(Color c,Vertex p0,Vertex p1,Vertex p2,Vertex p3) {
		this.col = c;
		pos[0]=new Vertex(p0.getColor(),p0.getVecValue());
		pos[1]=new Vertex(p1.getColor(),p1.getVecValue());
		pos[2]=new Vertex(p2.getColor(),p2.getVecValue());
		pos[3]=new Vertex(p3.getColor(),p3.getVecValue());
	}
	
	public void draw(Graphics g, Observer ob) {
		
	}
}
