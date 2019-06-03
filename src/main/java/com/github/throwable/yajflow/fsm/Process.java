package com.github.throwable.yajflow.fsm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/*

Define( 
	Eval( ... ),
	Eval( ... ),
	If( cond ).Then(
		Eval(...),
		Eval(...)
	).ElseIf(...).Then(
		...
	).Else(
		Eval(...),
		Eval(...)
	)
	While( cond ).Eval(
	)
	Try(
	).Catch( Exception1 ).Eval(
	).Catch( Exception2 ).Eval(
	).Finally(
	)
)
 */


public abstract class Process<In,Out> implements Serializable
{
	private static final long serialVersionUID = 1684403389238877064L;
	
	public static enum State {
		created, running, waiting, complete, aborted
	}
	
	/**
	 * Definition of tasks
	 */
	private transient Task mainTask;
	
	/******************************************************
	 * Stateful variables
	 */
	public State processState = State.created;
	public Stack<Integer> execPointer;
	
	
	/** Represents task that executes Runnable */
	protected static class Run extends Task
	{
		private transient Runnable runnable;
		
		public Run( Runnable runnable ) {
			this.runnable = runnable;
		}
		
		@Override
		protected boolean execute( ExecutionContext execCtx, int currDeepLevel )
		{
			if ( runnable != null )
				runnable.run();
			
			return true;
		}
	}

	
	/** Simple action */
	protected static abstract class Action extends Task {
		@Override
		protected boolean execute( ExecutionContext execCtx, int currDeepLevel )
		{
			Do();
			return true;
		}
		
		public abstract void Do();
	}
	
	/** Send message asynchronously (do not block process execution). Message will be
	 * 	accumulated in the output queue until process will block. */
	protected abstract class Output extends Task
	{
		public Output() {}
		
		@Override
		protected boolean execute( ExecutionContext execCtx, int currDeepLevel )
		{
			execCtx.output.add( send() );
			return true;
		}
		
		protected abstract Out send();
	}
	
	
	/** Receive message asynchronously (do not block process to receive input message) */
	protected abstract class Input extends Task {
		@Override
		@SuppressWarnings( value="unchecked" )
		protected boolean execute( ExecutionContext execCtx, int currDeepLevel )
		{
			receive( (In) execCtx.input );
			return true;
		}
		
		protected abstract void receive( In input );
	}
	
	
	/** Receive message synchronously: block process and wait for message */
	protected abstract class Receive extends SequenceTask
	{
		public Receive() {
			super( new ArrayList<Task>() );
			this.tasks.add( new YieldTask() );
			this.tasks.add( new Input() {
				@Override
				protected void receive( In input ) {
					Receive.this.receive( input );
				}
			} );
		}
		
		protected abstract void receive( In input );
	}
	
	
	/** Reply message synchronously: send message and block process execution */
	protected abstract class Reply extends Task
	{
		public Reply() {}
		
		@Override
		protected boolean execute( ExecutionContext execCtx, int currDeepLevel )
		{
			execCtx.output.add( reply() );
			return false;
		}
		
		protected abstract Out reply();
	}
	
	
	/** Request-Reply synchronous call: send request message and wait for reply */
	protected abstract class Call extends SequenceTask
	{
		public Call() {
			super( new ArrayList<Task>() );
			this.tasks.add(	new Output() {
				@Override protected Out send() {
					return request();
				}
				} );
			this.tasks.add( new YieldTask() );
			this.tasks.add( new Receive() {
					@Override protected void receive(In input) {
						reply( input );
					}
				} );
 		}
		
		public abstract Out request();
		public abstract void reply( In input );
	}

	
	/******************************************************
	 * Build tasks
	 */
	

	/** Define main process */
	protected abstract Task define();

	
	/** Simple runnable task */
	protected Task Run( Runnable code ) {
		return new Run( code );
	}


	/**
	 * Scoped Eval:
	 * Eval(
	 * 		Eval(...),
	 * 		Eval(...)
	 * )
	 */
	protected SequenceTask Do( Task... tasks ) {
		return new SequenceTask( Arrays.asList( tasks ) );
	}
	
	
	protected Action Log( final String string ) {
		return new Action() {
			public void Do() {
				System.out.println( string );
			}
		};
	}
	
	
	protected IfElseTask.IfTask.IfDef If(Check p )
	{
		return new IfElseTask.IfTask.IfDef( p );
	}
	
	protected WhileTask.WhileDef While( Check p )
	{
		return new WhileTask.WhileDef( p );
	}
	
	
	/********************************************************
	 * Execution tasks
	 */
	
	public State getState() {
		return processState;
	}
	
	
	/* do not touch -- may be called by parent process
	 * @Override
	protected boolean execute( Stack<Integer> calls, int currentDeepLevel ) 
	{
		boolean wait = super.execute( calls, currentDeepLevel );
		
	}*/
	
	
	@SuppressWarnings( value="unchecked" )
	public List<Out> exec( In input )
	{
		if ( mainTask == null )
			mainTask = define();
		
		switch ( processState ) {
		case created:
			this.execPointer = new Stack<Integer>();
		case waiting:
            // if an exception was thrown we rollback process state
			//this.state = State.running;
			ExecutionContext ctx = new ExecutionContext();
			ctx.execPointer = (Stack<Integer>) this.execPointer.clone();
			ctx.input = input;
			boolean complete = mainTask.execute( ctx, 0 );
			this.processState = complete ? State.complete : State.waiting;
            this.execPointer = ctx.execPointer;
			return (List<Out>) ctx.output;
			
		case running: throw new IllegalStateException( "Process is not in wait state" );
		case complete: throw new IllegalStateException( "Process is terminated" );
		case aborted: throw new IllegalStateException( "Process is aborted" );
		}
		
		return null;
	}


    public void abort() {
        this.processState = State.aborted;
    }
}
