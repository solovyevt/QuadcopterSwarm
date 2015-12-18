package utils;

public class Vector {

	public static final Vector ZERO = new Vector(0);
    public static final Vector UP = new Vector(0, 1, 0);
    public static final Vector DOWN = new Vector(0, -1, 0);
    public static final Vector NORTH = new Vector(0, 0, 1);
    public static final Vector SOUTH = new Vector(0, 0, -1);
    public static final Vector WEST = new Vector(-1, 0, 0);
    public static final Vector EAST = new Vector(1, 0, 0);
	
	public float x, y, z;
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

    public Vector(float c){
        x = c;
        y = c;
        z = c;
    }
	
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public float lengthSquared() {
		return x*x + y*y + z*z;
	}
	
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public Vector normalize() {
		float l = length();
		return new Vector(x/l, y/l, z/l);
	}
	
	static public Vector mul(Vector v, float c) {
		return new Vector(v.x*c, v.y*c, v.z*c);
	}
	
	public Vector add(float x, float y, float z) {
		return new Vector(this.x+x, this.y+y, this.z+z);
	}
	
	static public Vector add(Vector v1, Vector v2) {
		return new Vector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}

    static public Vector add(Vector... vectors) {
        Vector sum = new Vector(0, 0, 0);
        for(Vector v: vectors){
            sum = Vector.add(sum, v);
        }
        return sum;
    }

	static public Vector add(Vector v, float c){
		return new Vector(v.x + c, v.y + c, v.z + c);
	}
	
	public Vector sub(float x, float y, float z) {
		return add(-x, -y, -z);
	}
	
	static public Vector sub(Vector v1, Vector v2) {
		return new Vector(v1.x-v2.x, v1.y-v2.y, v1.z - v2.z);
	}
	
	static public float manhattanDistance(Vector v1, Vector v2) {
        return (Math.abs(v2.x - v1.x) + Math.abs(v2.y - v1.y) + Math.abs(v2.z - v1.z));
	}

    static public float euclidianDistance(Vector v1, Vector v2) {
        return sub(v2, v1).length();
    }
	
	static public float dot(Vector v1, Vector v2) {
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
	}
	
	static public Vector cross(Vector v1, Vector v2) {
		return new Vector(v1.y*v2.z - v1.z*v2.y, v1.z*v2.x - v1.x*v2.z, v1.x*v2.y - v1.y*v2.x);
	}
	
	public Vector min(Vector v) {
		return new Vector(Math.min(x, v.x), Math.min(y, v.y), Math.min(z, v.z));
	}
	
	public Vector max(Vector v) {
		return new Vector(Math.max(x, v.x), Math.max(y, v.y), Math.max(z, v.z));
	}
	
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
	
	public boolean equals(Vector v) {
		if (this==v) return true;
		return (x==v.x && y==v.y && z==v.z);
	}

	static public Vector div(Vector v, float c){
		return new Vector(v.x/c, v.y/c, v.z/c);
	}
}
