package nl.jchmb.wolves.ai.acceptor;

import java.util.Collection;

import nl.jchmb.wolves.ai.World;

public class DisjunctionAcceptor implements WorldAcceptor {

	private WorldAcceptor[] acceptors;
	
	public DisjunctionAcceptor(Collection<WorldAcceptor> acceptors) {
		this.acceptors = new WorldAcceptor[acceptors.size()];
		int i = 0;
		for (WorldAcceptor acceptor : acceptors) {
			this.acceptors[i++] = acceptor;
		}
	}
	
	public DisjunctionAcceptor(WorldAcceptor[] acceptors) {
		this.acceptors = acceptors;
	}
	
	@Override
	public boolean accept(World world) {
		for (WorldAcceptor acceptor : acceptors) {
			if (acceptor.accept(world)) {
				return true;
			}
		}
		return false;
	}
	
}
