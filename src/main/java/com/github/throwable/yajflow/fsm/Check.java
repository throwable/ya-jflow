package com.github.throwable.yajflow.fsm;

public abstract class Check
{
	public abstract boolean eval();
	
	public Check not() {
		return new Check() {
			@Override public boolean eval() {
				return !Check.this.eval();
			}
		};
	}
	
	public Check and( final Check expr ) {
		return new Check() {
			@Override public boolean eval() {
				return Check.this.eval() && expr.eval();
			}
		};
	}
	
	public Check or( final Check expr ) {
		return new Check() {
			@Override public boolean eval() {
				return Check.this.eval() || expr.eval();
			}
		};
	}


	public Check xor( final Check expr ) {
		return new Check() {
			@Override public boolean eval() {
				return Check.this.eval() ^ expr.eval();
			}
		};
	}
}
