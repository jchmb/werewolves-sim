package nl.jchmb.wolves.ai;

public class NullAcceptor implements WorldAcceptor {

	@Override
	public boolean accept(World world) {
		return true;
	}

}
