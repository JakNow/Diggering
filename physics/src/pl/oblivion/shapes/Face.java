package pl.oblivion.shapes;

import org.joml.Planef;
import org.joml.Vector3f;

public class Face {

	private Vertex vertex1;
	private Vertex vertex2;
	private Vertex vertex3;
	private Planef plane;

	public Face(Vertex vertex1, Vertex vertex2, Vertex vertex3) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.vertex3 = vertex3;
		this.plane = createPlane();
	}

	private Planef createPlane() {
		Vector3f A = new Vector3f(this.vertex1.pos);
		Vector3f B = new Vector3f(this.vertex2.pos);
		Vector3f C = new Vector3f(this.vertex3.pos);

		Vector3f cross = (new Vector3f(B).sub(A)).cross(C.sub(B));

		return new Planef(this.vertex1.pos, cross);
	}

	@Override
	public String toString() {
		return "Face{" +
				"vertex1=" + vertex1 +
				", vertex2=" + vertex2 +
				", vertex3=" + vertex3 +
				'}';
	}

	public Planef getPlane() {
		return plane;
	}

	public Vertex getVertex1() {
		return vertex1;
	}

	public void setVertex1(Vertex vertex1) {
		this.vertex1 = vertex1;
	}

	public Vertex getVertex2() {
		return vertex2;
	}

	public void setVertex2(Vertex vertex2) {
		this.vertex2 = vertex2;
	}

	public Vertex getVertex3() {
		return vertex3;
	}

	public void setVertex3(Vertex vertex3) {
		this.vertex3 = vertex3;
	}

	public static class Vertex {

		private Vector3f pos;
		private Vector3f norm;

		public Vertex(Vector3f pos, Vector3f norm) {
			this.pos = pos;
			this.norm = norm;
		}

		public Vector3f getPos() {
			return pos;
		}

		public void setPos(Vector3f pos) {
			this.pos = pos;
		}

		public Vector3f getNorm() {
			return norm;
		}

		public void setNorm(Vector3f norm) {
			this.norm = norm;
		}

		@Override
		public String toString() {
			return "Vertex{" +
					"pos=" + pos +
					", norm=" + norm +
					'}';
		}
	}
}
