package com.mathapollo.knowledge.contract;

import java.util.HashMap;

import org.omg.PortableInterceptor.NON_EXISTENT;


public class ConceptMock{
	
	private HashMap<String, Boolean> _internalHash;
	private HashMap<String, Boolean> _eventHash;
	private HashMap<String, Boolean> _commandHash;
	public ConceptMock()
	{
		_internalHash = new HashMap<String, Boolean>();
		_eventHash=new HashMap<String, Boolean>();
		_commandHash =new HashMap<String, Boolean>();
		InitializeHash();
	}
	
	
	
	
	
	
	
	private void InitializeHash()
	{
		_internalHash.put("circle", true);
		_internalHash.put("radius", true);
		
		//point concept 
		_internalHash.put("point", true);
		_internalHash.put("mid-point", true);// one type of point 
		_internalHash.put("midpoint", true);// one type of point 
		_internalHash.put("oringin", true);// one type of point 
		 _internalHash.put("central point", true);// one type of point 
		 _internalHash.put("point coordinate", true);
		 _internalHash.put("points coordinate", true);
		//_internalHash.put("(2,3)", true);
		//_internalHash.put("AB", true);
	     _internalHash.put("y-intercept", true);
		_internalHash.put("x-intercept", true);
		_internalHash.put("origin", true);
		_internalHash.put("endpoint", true);
		
		
		
		_internalHash.put("angle", true);
		
		// circle property Children member
		_internalHash.put("center", true);
		
	    _internalHash.put("radius", true);
	    _internalHash.put("slope", true);
	 // chord is also a concept instance of line segment 
	    _internalHash.put("chord", true);
	 // diameter is also a concept instance of " line segment", consist of end point 
	    _internalHash.put("diameter", true);
		
		//equation type property member 
		_internalHash.put("slope intercept form", true);
		_internalHash.put("general form", true);
		_internalHash.put("standard form", true);
		_internalHash.put("equation", true);
		_internalHash.put("Center-Radius form", true);
		
		// point children member 
		_internalHash.put("x-coordinate", true);
		_internalHash.put("y-coordinate", true);
		//point
		_internalHash.put("y-intercept", true);
		_internalHash.put("x-intercept", true);
		
		_internalHash.put("intersect point", true);
		
		// event hash 
				_eventHash.put("intersect",true);
				_eventHash.put("distance",true);
				_eventHash.put("tangent", true);
				_eventHash.put("reflection", true);
				_eventHash.put("perpendicular", true);
				_eventHash.put("parallel", true);
				
				_eventHash.put("equidistant", true);
				_eventHash.put("exceed", true);// exceed by 62
				_eventHash.put("larger", true); 
				_eventHash.put("apart", true); // distance
				// a triangle can be inscribed in a circle
				_eventHash.put("inscribed", true); // special event inscribe circle/square of triangle or rectangle 
				_eventHash.put("circumscribed", true);
				
				_eventHash.put("perpendicular distance", true);
	   //command  action hash 
	     _commandHash.put("graph", true);
	     
	  // line and line segment concepts 
			_internalHash.put("line", true);
	        _internalHash.put("y-axis", true);
	        _internalHash.put("x-axis", true);
	     // chord should be one kinds of line segment 
	        _internalHash.put("line segment", true);
	        
	  // Angle 
	     
	 // triangle 
	     
	// rectangle
			_internalHash.put("rectangle", true);
			_internalHash.put("perimeter", true);
	     
	     
	     
	     
	     
//		_internalHash.put("line", true);
//		_internalHash.put("line", true);
//		_internalHash.put("line", true);
//		_internalHash.put("line", true);
//		_internalHash.put("line", true);		
	}
	
	public String ContainToken(String token)
	{
		//approximate string matching 
		//input: the line standard form
		//pattern: line standard form
		int maxlength=0; 
	    String pattern1=null;
		for(String pattern : _internalHash.keySet())
		{
			if(token.toLowerCase().contains(pattern.toLowerCase()))
			{
		      if(pattern.length()>maxlength)
		      {
		    	  maxlength=pattern.length();
		    	  pattern1=new String();
		    	  pattern1=pattern;
		      }
			}
		}
		
		
		return pattern1;
	}
	
	public String ContainEvent(String token)
	{
		//approximate string matching 
		//input: the line standard form
		//pattern: line standard form
		int maxlength=0; 
	    String pattern1=null;
		for(String pattern : _eventHash.keySet())
		{
			if(token.toLowerCase().contains(pattern.toLowerCase()))
			{
		      if(pattern.length()>maxlength)
		      {
		    	  maxlength=pattern.length();
		    	  pattern1=new String();
		    	  pattern1=pattern;
		      }
			}
		
		}
		return pattern1;
	}	
	
}
