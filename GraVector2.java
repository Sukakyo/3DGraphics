package graphics3d;



class Vector3d{
  double xpram,ypram,zpram;

  public Vector3d(double x,double y,double z){
    this.xpram=x;
    this.ypram=y;
    this.zpram=z;
  }

  public void move(double xdis,double ydis,double zdis){
    this.xpram+=xdis;
    this.ypram+=ydis;
    this.zpram+=zdis;
  }

  public void horizontal_turn(double theta){
	double x = this.xpram;
	double y = this.ypram;
    this.xpram=(x*Math.cos(theta))-(y*Math.sin(theta));
    this.ypram=(x*Math.sin(theta))+(y*Math.cos(theta));
    //System.out.println(this.xpram+" "+this.ypram);
    //System.out.println(Vector3dOpe.norm(this));
  }
  
  public void vertical_turn(double fi) {
	  this.xpram=(this.xpram*Math.cos(fi));
	  this.ypram=(this.ypram*Math.cos(fi));
	  this.zpram=(this.zpram*Math.sin(fi));
  }
  
  public void setXpram(double x){
	  this.xpram=x;
  }
  
  public void setYpram(double y){
	  this.ypram=y;
  }
  
  public void setZpram(double z){
	  this.zpram=z;
  }
  
  public void setVec(Vector3d v){
	  setXpram(v.getXpram());
	  setYpram(v.getYpram());
	  setZpram(v.getZpram());
  }
  
  public void setPram(double x, double y, double z) {
	  setXpram(x);
	  setYpram(y);
	  setZpram(z);
  }

  public double getXpram(){
    return this.xpram;
  }

  public double getYpram(){
    return this.ypram;
  }

  public double getZpram(){
    return this.zpram;
  }
  
  public static Vector3d copyVec(Vector3d v) {
	  double x,y,z;
	  x=v.getXpram();
	  y=v.getYpram();
	  z=v.getZpram();
	  Vector3d new_v = new Vector3d(x,y,z);
	  
	  return new_v;
  }
  
}

class Vector3dOpe {
	
  //v1にv2を代入
  public static void assign(Vector3d v1, Vector3d v2) {
	  v1.setVec(v2);
  }

  //vのノルム
  public static double norm(Vector3d v){
      return (Math.sqrt(v.getXpram()*v.getXpram()
                       +v.getYpram()*v.getYpram()
                       +v.getZpram()*v.getZpram()));
  }

  //ベクトルの加法
  public static Vector3d addiction(Vector3d v1, Vector3d v2){
    double x = v1.getXpram()+v2.getXpram();
    double y = v1.getYpram()+v2.getYpram();
    double z = v1.getZpram()+v2.getZpram();

    return new Vector3d(x,y,z);
  }

  //ベクトルの減法
  public static Vector3d subtraction(Vector3d v1, Vector3d v2){
    double x = v1.getXpram()-v2.getXpram();
    double y = v1.getYpram()-v2.getYpram();
    double z = v1.getZpram()-v2.getZpram();

    return new Vector3d(x,y,z);
  }
  
  //ベクトルのスカラー倍
  public static Vector3d scholarMulti(double lamda, Vector3d v) {
	  Vector3d buf;
	  double bx=(lamda*v.getXpram());
	  double by=(lamda*v.getYpram());
	  double bz=(lamda*v.getZpram());
	  buf = new Vector3d(bx,by,bz);
	  return buf;
  }
  
  //ベクトルの内積
  public static double dotPro(Vector3d v1,Vector3d v2){
      return v1.getXpram()*v2.getXpram()
            +v1.getYpram()*v2.getYpram()
            +v1.getZpram()*v2.getZpram();
  }
  
  //ベクトルの外積
  public static Vector3d crossPro(Vector3d v1,Vector3d v2){
      double cXpram = v1.getYpram()*v2.getZpram()
                     -v1.getZpram()*v2.getYpram();
      double cYpram = v1.getZpram()*v2.getXpram()
                     -v1.getXpram()*v2.getZpram();
      double cZpram = v1.getXpram()*v2.getYpram()
                     -v1.getYpram()*v2.getXpram();
  
      Vector3d vResult = new Vector3d(cXpram,cYpram,cZpram);
      return vResult;
  }
  
  //ベクトルのなす角(cos)
  public static double angleCos(Vector3d v1, Vector3d v2) {
	  double d = Vector3dOpe.dotPro(v1,v2);
	  double n1 = Vector3dOpe.norm(v1);
	  double n2 = Vector3dOpe.norm(v2);
	  
	  return d/n1/n2;
  }
  
  //ベクトルのなす角(sin)
  public static double angleSin(Vector3d v1,Vector3d v2,Vector3d pitVec) {
	  Vector3d v = Vector3dOpe.crossPro(v1,v2);
	  double d = Vector3dOpe.norm(v);
	  double n1 = Vector3dOpe.norm(v1);
	  double n2 = Vector3dOpe.norm(v2);
	  
	  double mod_sin = d/n1/n2;
	  

	  double direct = Vector3dOpe.dotPro(v, pitVec);
	  
	  if(direct >= 0) {
		  return mod_sin;
	  }else {
		  return -mod_sin;
	  }
  }
}
