package nl.jchmb.wolves.ai.acceptor;

import nl.jchmb.wolves.ai.World;

public class NullAcceptor implements WorldAcceptor {

	@Override
	public boolean accept(World world) {
		return true;
	}

}
