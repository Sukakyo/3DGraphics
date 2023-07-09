package graphics3d;

class Observer{
	Vector3d pos;
	Vector3d pers;
	Vector3d basis;
	
	/* gravを操作して方向を自由に操作できるが、複雑であるため別の機会に */
	Vector3d grav = new Vector3d(0,0,1);

	public Observer(double x, double y, double z,Vector3d v) {
		pos = new Vector3d(x, y, z);
		pers = v;
		
		basis = Vector3dOpe.crossPro(grav,pers);
		
	}
	
	public void move(double xdis,double ydis,double zdis){
		pos.move(xdis, ydis, zdis);
	}
	
	public void horizontal_turn(double theta) {
		pers.horizontal_turn(theta);
	}
	
	public void vertical_turn(double fi) {
		pers.vertical_turn(fi);
	}
}

class Ray{
	Vector3d beam;//光束
	
	public Ray(double x,double y,double z) {
		beam=new Vector3d(x,y,z);
	}
	
    public double getRayX() {
    	return this.beam.getXpram();
    }
    
    public double getRayY() {
    	return this.beam.getYpram();
    }
    
    public double getRayZ() {
    	return this.beam.getZpram();
    }
    
    public Vector3d getRayBeam() {
    	return this.beam;
    }

}
