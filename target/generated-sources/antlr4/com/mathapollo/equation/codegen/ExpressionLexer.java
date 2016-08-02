// Generated from Expression.g4 by ANTLR 4.4

package com.mathapollo.equation.codegen;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__12=1, T__11=2, T__10=3, T__9=4, T__8=5, T__7=6, T__6=7, T__5=8, T__4=9, 
		T__3=10, T__2=11, T__1=12, T__0=13, WS=14, Value=15, Symbol=16, Variable=17, 
		Const=18;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'"
	};
	public static final String[] ruleNames = {
		"T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", 
		"T__3", "T__2", "T__1", "T__0", "WS", "Value", "Symbol", "Variable", "Const"
	};


	public ExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Expression.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\24k\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b"+
		"\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\6\17E\n"+
		"\17\r\17\16\17F\3\17\3\17\3\20\3\20\3\20\5\20N\n\20\3\21\3\21\5\21R\n"+
		"\21\3\22\3\22\5\22V\n\22\3\23\6\23Y\n\23\r\23\16\23Z\3\23\7\23^\n\23\f"+
		"\23\16\23a\13\23\3\23\3\23\7\23e\n\23\f\23\16\23h\13\23\5\23j\n\23\2\2"+
		"\24\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\3\2\6\5\2\13\f\17\17\"\"\3\2C\\\3\2c|\3\2\62;s\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\3\'\3\2\2\2\5)\3\2\2\2\7,\3\2\2\2\t.\3\2\2\2\13\60\3\2\2"+
		"\2\r\62\3\2\2\2\17\64\3\2\2\2\21\67\3\2\2\2\239\3\2\2\2\25;\3\2\2\2\27"+
		"=\3\2\2\2\31?\3\2\2\2\33A\3\2\2\2\35D\3\2\2\2\37M\3\2\2\2!O\3\2\2\2#S"+
		"\3\2\2\2%i\3\2\2\2\'(\7\61\2\2(\4\3\2\2\2)*\7@\2\2*+\7?\2\2+\6\3\2\2\2"+
		",-\7>\2\2-\b\3\2\2\2./\7?\2\2/\n\3\2\2\2\60\61\7`\2\2\61\f\3\2\2\2\62"+
		"\63\7@\2\2\63\16\3\2\2\2\64\65\7>\2\2\65\66\7?\2\2\66\20\3\2\2\2\678\7"+
		"*\2\28\22\3\2\2\29:\7+\2\2:\24\3\2\2\2;<\7,\2\2<\26\3\2\2\2=>\7-\2\2>"+
		"\30\3\2\2\2?@\7.\2\2@\32\3\2\2\2AB\7/\2\2B\34\3\2\2\2CE\t\2\2\2DC\3\2"+
		"\2\2EF\3\2\2\2FD\3\2\2\2FG\3\2\2\2GH\3\2\2\2HI\b\17\2\2I\36\3\2\2\2JN"+
		"\5!\21\2KN\5%\23\2LN\5#\22\2MJ\3\2\2\2MK\3\2\2\2ML\3\2\2\2N \3\2\2\2O"+
		"Q\t\3\2\2PR\t\3\2\2QP\3\2\2\2QR\3\2\2\2R\"\3\2\2\2SU\t\4\2\2TV\t\4\2\2"+
		"UT\3\2\2\2UV\3\2\2\2V$\3\2\2\2WY\t\5\2\2XW\3\2\2\2YZ\3\2\2\2ZX\3\2\2\2"+
		"Z[\3\2\2\2[j\3\2\2\2\\^\t\5\2\2]\\\3\2\2\2^a\3\2\2\2_]\3\2\2\2_`\3\2\2"+
		"\2`b\3\2\2\2a_\3\2\2\2bf\7\60\2\2ce\t\5\2\2dc\3\2\2\2eh\3\2\2\2fd\3\2"+
		"\2\2fg\3\2\2\2gj\3\2\2\2hf\3\2\2\2iX\3\2\2\2i_\3\2\2\2j&\3\2\2\2\13\2"+
		"FMQUZ_fi\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}