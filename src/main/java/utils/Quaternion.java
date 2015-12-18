package utils;

public class Quaternion {
	
	public static final Quaternion IDENTITY = new Quaternion(0, 0, 0, 1);
	public static final Quaternion UNIT_X = new Quaternion(1, 0, 0, 0);
	public static final Quaternion UNIT_Y = new Quaternion(0, 1, 0, 0);
	public static final Quaternion UNIT_Z = new Quaternion(0, 0, 1, 0);
	
	public final float x, y, z, w;
	private Vector cachedAxisAngle;
	
	public Quaternion (float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quaternion(Quaternion q) {
		this.x = q.x;
		this.y = q.y;
		this.z = q.z;
		this.w = q.w;
	}
	
	public Quaternion(float angle, Vector axis) {
		double rads = Math.toRadians(angle);
		double halfangle = Math.sin(rads / 2);
		this.x = (float) (axis.x * halfangle);
		this.y = (float) (axis.y * halfangle);
		this.z = (float) (axis.z * halfangle);
		this.w = (float) Math.cos(rads / 2);
	}
	
	public Quaternion(Vector direction, Vector from) {
		double angle = Math.acos(Vector.dot(direction, from));
		Vector axis = Vector.cross(from, direction).normalize();
		
		double halfangle = Math.sin(angle / 2);
		this.x = (float) (axis.x * halfangle);
		this.y = (float) (axis.y * halfangle);
		this.z = (float) (axis.z * halfangle);
		this.w = (float) Math.cos(angle / 2);
	}
	
	public static Quaternion quatFromDir(Vector v) {
		Quaternion qYaw = new Quaternion(new Vector(v.x, 0, v.z).normalize(), Vector.NORTH).normalize();
		Quaternion qPitch = new Quaternion(new Vector(0, v.y, v.z).normalize(), Vector.NORTH).normalize();
		return qYaw.mul(qPitch).normalize();
	}
	
	public float correctAngle(float angle) {
		if (angle<0) {
			angle+=360;
		}
		if (angle>360) {
			angle-=360;
		}
		return angle;
	}
	
	public float getPitch() {
		getAxisAngle();
		return cachedAxisAngle.x;
	}
	
	public float getYaw() {
		getAxisAngle();
		return cachedAxisAngle.y;
	}
	
	public float getRoll() {
		getAxisAngle();
		return cachedAxisAngle.z;
	}
	
	public Vector getForwardVector() {
    	float x = (float) (-Math.cos(Math.toRadians(getPitch()))*Math.sin(Math.toRadians(180-getYaw()+getRoll())));
    	float y = (float) (Math.sin(Math.toRadians(getPitch())));
    	float z = (float) (Math.cos(Math.toRadians(getPitch()))*Math.cos(Math.toRadians(180-getYaw()+getRoll())));
    	return new Vector(x, y, z).normalize();
    }
	
	public Vector getAxisAngle() {
		if (cachedAxisAngle!=null)
			return cachedAxisAngle;
		
		final float q0 = w;
		final float q1 = z;
		final float q2 = x;
		final float q3 = y;
		
		final double r1, r2, r3, test;
		test = q0 *q2 - q3*q1;
		
		if (Math.abs(test) < 4.999) {
			r1 = Math.atan2(2*(q0*q1+q2*q3), 1-2*(q1*q1+q2*q2));
			r2 = Math.asin(2*test);
			r3 = Math.atan2(2*(q0*q3+q1*q2), 1-2*(q2*q2+q3*q3));
		} else {
			int sign = (test<0) ? -1 : 1;
			r1 = 0;
			r2 = sign*Math.PI/2;
			r3 = -sign*2*Math.atan2(q1, q0);
		}
		
		final float roll = (float) Math.toDegrees(r1);
		final float pitch = (float) Math.toDegrees(r2);
		float yaw = (float) Math.toDegrees(r3);
		
		if (yaw > 180)
			yaw -= 360;
		else if (yaw < -180)
			yaw += 360;
		
		cachedAxisAngle = new Vector(correctAngle(pitch), correctAngle(yaw), correctAngle(roll));
		return cachedAxisAngle;
	}
	
	public float lengthSquared() {
		return x*x + y*y + z*z + w*w;
	}
	
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public Quaternion normalize() {
		float l = length();
		return new Quaternion(x/l, y/l, z/l, w/l);
	}

	public Quaternion mul(Quaternion q) {
		float _x = w*q.x + x*q.w + y*q.z - z*q.y;
		float _y = w*q.y + y*q.w + z*q.x - x*q.z;
		float _z = w*q.z + z*q.w + x*q.y - y*q.x;
		float _w = w*q.w - x*q.x - y*q.y - z*q.z;
		return new Quaternion(_x, _y, _z, _w);
	}
	
	public Quaternion rotate(float angle, Vector axis) {
		return new Quaternion(angle, axis).mul(this);
	}
	
	public Quaternion rotate(float angle, float x, float y, float z) {
		return new Quaternion(angle, new Vector(x, y, z)).mul(this);
	}
	
}
