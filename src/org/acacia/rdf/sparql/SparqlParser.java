package org.acacia.rdf.sparql;

// $ANTLR 3.5.2 /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g 2015-07-03 14:44:11

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class SparqlParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "A", "AND", "ANY", "ASC", "ASK", 
		"ASTERISK", "BASE", "BLANK_NODE_LABEL", "BOUND", "BY", "CLOSE_BRACE", 
		"CLOSE_CURLY_BRACE", "CLOSE_SQUARE_BRACE", "COMMA", "COMMENT", "CONSTRUCT", 
		"DATATYPE", "DECIMAL", "DECIMAL_NEGATIVE", "DECIMAL_POSITIVE", "DESC", 
		"DESCRIBE", "DIGIT", "DISTINCT", "DIVIDE", "DOT", "DOUBLE", "DOUBLE_NEGATIVE", 
		"DOUBLE_POSITIVE", "ECHAR", "EOL", "EQUAL", "EXPONENT", "FALSE", "FILTER", 
		"FROM", "GRAPH", "GREATER", "GREATER_EQUAL", "INTEGER", "INTEGER_NEGATIVE", 
		"INTEGER_POSITIVE", "IRI_REF", "ISBLANK", "ISIRI", "ISLITERAL", "ISURI", 
		"LANG", "LANGMATCHES", "LANGTAG", "LESS", "LESS_EQUAL", "LIMIT", "MINUS", 
		"NAMED", "NOT", "NOT_EQUAL", "OFFSET", "OPEN_BRACE", "OPEN_CURLY_BRACE", 
		"OPEN_SQUARE_BRACE", "OPTIONAL", "OR", "ORDER", "PLUS", "PNAME_LN", "PNAME_NS", 
		"PN_CHARS", "PN_CHARS_BASE", "PN_CHARS_U", "PN_LOCAL", "PN_PREFIX", "PREFIX", 
		"REDUCED", "REFERENCE", "REGEX", "SAMETERM", "SELECT", "SEMICOLON", "STR", 
		"STRING_LITERAL1", "STRING_LITERAL2", "STRING_LITERAL_LONG1", "STRING_LITERAL_LONG2", 
		"TRUE", "UNION", "VAR1", "VAR2", "VARNAME", "WHERE", "WS"
	};
	public static final int EOF=-1;
	public static final int A=4;
	public static final int AND=5;
	public static final int ANY=6;
	public static final int ASC=7;
	public static final int ASK=8;
	public static final int ASTERISK=9;
	public static final int BASE=10;
	public static final int BLANK_NODE_LABEL=11;
	public static final int BOUND=12;
	public static final int BY=13;
	public static final int CLOSE_BRACE=14;
	public static final int CLOSE_CURLY_BRACE=15;
	public static final int CLOSE_SQUARE_BRACE=16;
	public static final int COMMA=17;
	public static final int COMMENT=18;
	public static final int CONSTRUCT=19;
	public static final int DATATYPE=20;
	public static final int DECIMAL=21;
	public static final int DECIMAL_NEGATIVE=22;
	public static final int DECIMAL_POSITIVE=23;
	public static final int DESC=24;
	public static final int DESCRIBE=25;
	public static final int DIGIT=26;
	public static final int DISTINCT=27;
	public static final int DIVIDE=28;
	public static final int DOT=29;
	public static final int DOUBLE=30;
	public static final int DOUBLE_NEGATIVE=31;
	public static final int DOUBLE_POSITIVE=32;
	public static final int ECHAR=33;
	public static final int EOL=34;
	public static final int EQUAL=35;
	public static final int EXPONENT=36;
	public static final int FALSE=37;
	public static final int FILTER=38;
	public static final int FROM=39;
	public static final int GRAPH=40;
	public static final int GREATER=41;
	public static final int GREATER_EQUAL=42;
	public static final int INTEGER=43;
	public static final int INTEGER_NEGATIVE=44;
	public static final int INTEGER_POSITIVE=45;
	public static final int IRI_REF=46;
	public static final int ISBLANK=47;
	public static final int ISIRI=48;
	public static final int ISLITERAL=49;
	public static final int ISURI=50;
	public static final int LANG=51;
	public static final int LANGMATCHES=52;
	public static final int LANGTAG=53;
	public static final int LESS=54;
	public static final int LESS_EQUAL=55;
	public static final int LIMIT=56;
	public static final int MINUS=57;
	public static final int NAMED=58;
	public static final int NOT=59;
	public static final int NOT_EQUAL=60;
	public static final int OFFSET=61;
	public static final int OPEN_BRACE=62;
	public static final int OPEN_CURLY_BRACE=63;
	public static final int OPEN_SQUARE_BRACE=64;
	public static final int OPTIONAL=65;
	public static final int OR=66;
	public static final int ORDER=67;
	public static final int PLUS=68;
	public static final int PNAME_LN=69;
	public static final int PNAME_NS=70;
	public static final int PN_CHARS=71;
	public static final int PN_CHARS_BASE=72;
	public static final int PN_CHARS_U=73;
	public static final int PN_LOCAL=74;
	public static final int PN_PREFIX=75;
	public static final int PREFIX=76;
	public static final int REDUCED=77;
	public static final int REFERENCE=78;
	public static final int REGEX=79;
	public static final int SAMETERM=80;
	public static final int SELECT=81;
	public static final int SEMICOLON=82;
	public static final int STR=83;
	public static final int STRING_LITERAL1=84;
	public static final int STRING_LITERAL2=85;
	public static final int STRING_LITERAL_LONG1=86;
	public static final int STRING_LITERAL_LONG2=87;
	public static final int TRUE=88;
	public static final int UNION=89;
	public static final int VAR1=90;
	public static final int VAR2=91;
	public static final int VARNAME=92;
	public static final int WHERE=93;
	public static final int WS=94;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public SparqlParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public SparqlParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return SparqlParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g"; }



	// $ANTLR start "query"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:61:1: query : prologue ( selectQuery | constructQuery | describeQuery | askQuery ) EOF ;
	public final void query() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:62:5: ( prologue ( selectQuery | constructQuery | describeQuery | askQuery ) EOF )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:62:7: prologue ( selectQuery | constructQuery | describeQuery | askQuery ) EOF
			{
			pushFollow(FOLLOW_prologue_in_query19);
			prologue();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:62:16: ( selectQuery | constructQuery | describeQuery | askQuery )
			int alt1=4;
			switch ( input.LA(1) ) {
			case SELECT:
				{
				alt1=1;
				}
				break;
			case CONSTRUCT:
				{
				alt1=2;
				}
				break;
			case DESCRIBE:
				{
				alt1=3;
				}
				break;
			case ASK:
				{
				alt1=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}
			switch (alt1) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:62:18: selectQuery
					{
					pushFollow(FOLLOW_selectQuery_in_query23);
					selectQuery();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:62:32: constructQuery
					{
					pushFollow(FOLLOW_constructQuery_in_query27);
					constructQuery();
					state._fsp--;

					}
					break;
				case 3 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:62:49: describeQuery
					{
					pushFollow(FOLLOW_describeQuery_in_query31);
					describeQuery();
					state._fsp--;

					}
					break;
				case 4 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:62:65: askQuery
					{
					pushFollow(FOLLOW_askQuery_in_query35);
					askQuery();
					state._fsp--;

					}
					break;

			}

			match(input,EOF,FOLLOW_EOF_in_query39); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "query"



	// $ANTLR start "prologue"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:65:1: prologue : ( baseDecl )? ( prefixDecl )* ;
	public final void prologue() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:66:5: ( ( baseDecl )? ( prefixDecl )* )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:66:7: ( baseDecl )? ( prefixDecl )*
			{
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:66:7: ( baseDecl )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==BASE) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:66:7: baseDecl
					{
					pushFollow(FOLLOW_baseDecl_in_prologue56);
					baseDecl();
					state._fsp--;

					}
					break;

			}

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:66:17: ( prefixDecl )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==PREFIX) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:66:17: prefixDecl
					{
					pushFollow(FOLLOW_prefixDecl_in_prologue59);
					prefixDecl();
					state._fsp--;

					}
					break;

				default :
					break loop3;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "prologue"



	// $ANTLR start "baseDecl"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:69:1: baseDecl : BASE IRI_REF ;
	public final void baseDecl() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:70:5: ( BASE IRI_REF )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:70:7: BASE IRI_REF
			{
			match(input,BASE,FOLLOW_BASE_in_baseDecl77); 
			match(input,IRI_REF,FOLLOW_IRI_REF_in_baseDecl79); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "baseDecl"



	// $ANTLR start "prefixDecl"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:73:1: prefixDecl : PREFIX PNAME_NS IRI_REF ;
	public final void prefixDecl() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:74:5: ( PREFIX PNAME_NS IRI_REF )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:74:7: PREFIX PNAME_NS IRI_REF
			{
			match(input,PREFIX,FOLLOW_PREFIX_in_prefixDecl96); 
			match(input,PNAME_NS,FOLLOW_PNAME_NS_in_prefixDecl98); 
			match(input,IRI_REF,FOLLOW_IRI_REF_in_prefixDecl100); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "prefixDecl"



	// $ANTLR start "selectQuery"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:77:1: selectQuery : SELECT ( DISTINCT | REDUCED )? ( ( var )+ | ASTERISK ) ( datasetClause )* whereClause solutionModifier ;
	public final void selectQuery() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:78:5: ( SELECT ( DISTINCT | REDUCED )? ( ( var )+ | ASTERISK ) ( datasetClause )* whereClause solutionModifier )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:78:7: SELECT ( DISTINCT | REDUCED )? ( ( var )+ | ASTERISK ) ( datasetClause )* whereClause solutionModifier
			{
			match(input,SELECT,FOLLOW_SELECT_in_selectQuery117); 
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:78:14: ( DISTINCT | REDUCED )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==DISTINCT||LA4_0==REDUCED) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
					{
					if ( input.LA(1)==DISTINCT||input.LA(1)==REDUCED ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;

			}

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:78:38: ( ( var )+ | ASTERISK )
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( ((LA6_0 >= VAR1 && LA6_0 <= VAR2)) ) {
				alt6=1;
			}
			else if ( (LA6_0==ASTERISK) ) {
				alt6=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:78:40: ( var )+
					{
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:78:40: ( var )+
					int cnt5=0;
					loop5:
					while (true) {
						int alt5=2;
						int LA5_0 = input.LA(1);
						if ( ((LA5_0 >= VAR1 && LA5_0 <= VAR2)) ) {
							alt5=1;
						}

						switch (alt5) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:78:40: var
							{
							pushFollow(FOLLOW_var_in_selectQuery132);
							var();
							state._fsp--;

							}
							break;

						default :
							if ( cnt5 >= 1 ) break loop5;
							EarlyExitException eee = new EarlyExitException(5, input);
							throw eee;
						}
						cnt5++;
					}

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:78:47: ASTERISK
					{
					match(input,ASTERISK,FOLLOW_ASTERISK_in_selectQuery137); 
					}
					break;

			}

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:78:58: ( datasetClause )*
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( (LA7_0==FROM) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:78:58: datasetClause
					{
					pushFollow(FOLLOW_datasetClause_in_selectQuery141);
					datasetClause();
					state._fsp--;

					}
					break;

				default :
					break loop7;
				}
			}

			pushFollow(FOLLOW_whereClause_in_selectQuery144);
			whereClause();
			state._fsp--;

			pushFollow(FOLLOW_solutionModifier_in_selectQuery146);
			solutionModifier();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "selectQuery"



	// $ANTLR start "constructQuery"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:81:1: constructQuery : CONSTRUCT constructTemplate ( datasetClause )* whereClause solutionModifier ;
	public final void constructQuery() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:82:5: ( CONSTRUCT constructTemplate ( datasetClause )* whereClause solutionModifier )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:82:7: CONSTRUCT constructTemplate ( datasetClause )* whereClause solutionModifier
			{
			match(input,CONSTRUCT,FOLLOW_CONSTRUCT_in_constructQuery163); 
			pushFollow(FOLLOW_constructTemplate_in_constructQuery165);
			constructTemplate();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:82:35: ( datasetClause )*
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( (LA8_0==FROM) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:82:35: datasetClause
					{
					pushFollow(FOLLOW_datasetClause_in_constructQuery167);
					datasetClause();
					state._fsp--;

					}
					break;

				default :
					break loop8;
				}
			}

			pushFollow(FOLLOW_whereClause_in_constructQuery170);
			whereClause();
			state._fsp--;

			pushFollow(FOLLOW_solutionModifier_in_constructQuery172);
			solutionModifier();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "constructQuery"



	// $ANTLR start "describeQuery"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:85:1: describeQuery : DESCRIBE ( ( varOrIRIref )+ | ASTERISK ) ( datasetClause )* ( whereClause )? solutionModifier ;
	public final void describeQuery() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:5: ( DESCRIBE ( ( varOrIRIref )+ | ASTERISK ) ( datasetClause )* ( whereClause )? solutionModifier )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:7: DESCRIBE ( ( varOrIRIref )+ | ASTERISK ) ( datasetClause )* ( whereClause )? solutionModifier
			{
			match(input,DESCRIBE,FOLLOW_DESCRIBE_in_describeQuery189); 
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:16: ( ( varOrIRIref )+ | ASTERISK )
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==IRI_REF||(LA10_0 >= PNAME_LN && LA10_0 <= PNAME_NS)||(LA10_0 >= VAR1 && LA10_0 <= VAR2)) ) {
				alt10=1;
			}
			else if ( (LA10_0==ASTERISK) ) {
				alt10=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				throw nvae;
			}

			switch (alt10) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:18: ( varOrIRIref )+
					{
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:18: ( varOrIRIref )+
					int cnt9=0;
					loop9:
					while (true) {
						int alt9=2;
						int LA9_0 = input.LA(1);
						if ( (LA9_0==IRI_REF||(LA9_0 >= PNAME_LN && LA9_0 <= PNAME_NS)||(LA9_0 >= VAR1 && LA9_0 <= VAR2)) ) {
							alt9=1;
						}

						switch (alt9) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:18: varOrIRIref
							{
							pushFollow(FOLLOW_varOrIRIref_in_describeQuery193);
							varOrIRIref();
							state._fsp--;

							}
							break;

						default :
							if ( cnt9 >= 1 ) break loop9;
							EarlyExitException eee = new EarlyExitException(9, input);
							throw eee;
						}
						cnt9++;
					}

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:33: ASTERISK
					{
					match(input,ASTERISK,FOLLOW_ASTERISK_in_describeQuery198); 
					}
					break;

			}

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:44: ( datasetClause )*
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==FROM) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:44: datasetClause
					{
					pushFollow(FOLLOW_datasetClause_in_describeQuery202);
					datasetClause();
					state._fsp--;

					}
					break;

				default :
					break loop11;
				}
			}

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:59: ( whereClause )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==OPEN_CURLY_BRACE||LA12_0==WHERE) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:86:59: whereClause
					{
					pushFollow(FOLLOW_whereClause_in_describeQuery205);
					whereClause();
					state._fsp--;

					}
					break;

			}

			pushFollow(FOLLOW_solutionModifier_in_describeQuery208);
			solutionModifier();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "describeQuery"



	// $ANTLR start "askQuery"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:89:1: askQuery : ASK ( datasetClause )* whereClause ;
	public final void askQuery() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:90:5: ( ASK ( datasetClause )* whereClause )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:90:7: ASK ( datasetClause )* whereClause
			{
			match(input,ASK,FOLLOW_ASK_in_askQuery225); 
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:90:11: ( datasetClause )*
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( (LA13_0==FROM) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:90:11: datasetClause
					{
					pushFollow(FOLLOW_datasetClause_in_askQuery227);
					datasetClause();
					state._fsp--;

					}
					break;

				default :
					break loop13;
				}
			}

			pushFollow(FOLLOW_whereClause_in_askQuery230);
			whereClause();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "askQuery"



	// $ANTLR start "datasetClause"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:93:1: datasetClause : FROM ( defaultGraphClause | namedGraphClause ) ;
	public final void datasetClause() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:94:5: ( FROM ( defaultGraphClause | namedGraphClause ) )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:94:7: FROM ( defaultGraphClause | namedGraphClause )
			{
			match(input,FROM,FOLLOW_FROM_in_datasetClause247); 
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:94:12: ( defaultGraphClause | namedGraphClause )
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==IRI_REF||(LA14_0 >= PNAME_LN && LA14_0 <= PNAME_NS)) ) {
				alt14=1;
			}
			else if ( (LA14_0==NAMED) ) {
				alt14=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}

			switch (alt14) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:94:14: defaultGraphClause
					{
					pushFollow(FOLLOW_defaultGraphClause_in_datasetClause251);
					defaultGraphClause();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:94:35: namedGraphClause
					{
					pushFollow(FOLLOW_namedGraphClause_in_datasetClause255);
					namedGraphClause();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "datasetClause"



	// $ANTLR start "defaultGraphClause"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:97:1: defaultGraphClause : sourceSelector ;
	public final void defaultGraphClause() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:98:5: ( sourceSelector )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:98:7: sourceSelector
			{
			pushFollow(FOLLOW_sourceSelector_in_defaultGraphClause274);
			sourceSelector();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "defaultGraphClause"



	// $ANTLR start "namedGraphClause"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:101:1: namedGraphClause : NAMED sourceSelector ;
	public final void namedGraphClause() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:102:5: ( NAMED sourceSelector )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:102:7: NAMED sourceSelector
			{
			match(input,NAMED,FOLLOW_NAMED_in_namedGraphClause291); 
			pushFollow(FOLLOW_sourceSelector_in_namedGraphClause293);
			sourceSelector();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "namedGraphClause"



	// $ANTLR start "sourceSelector"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:105:1: sourceSelector : iriRef ;
	public final void sourceSelector() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:106:5: ( iriRef )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:106:7: iriRef
			{
			pushFollow(FOLLOW_iriRef_in_sourceSelector310);
			iriRef();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "sourceSelector"



	// $ANTLR start "whereClause"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:109:1: whereClause : ( WHERE )? groupGraphPattern ;
	public final void whereClause() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:110:5: ( ( WHERE )? groupGraphPattern )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:110:7: ( WHERE )? groupGraphPattern
			{
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:110:7: ( WHERE )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==WHERE) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:110:7: WHERE
					{
					match(input,WHERE,FOLLOW_WHERE_in_whereClause327); 
					}
					break;

			}

			pushFollow(FOLLOW_groupGraphPattern_in_whereClause330);
			groupGraphPattern();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "whereClause"



	// $ANTLR start "solutionModifier"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:113:1: solutionModifier : ( orderClause )? ( limitOffsetClauses )? ;
	public final void solutionModifier() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:114:5: ( ( orderClause )? ( limitOffsetClauses )? )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:114:7: ( orderClause )? ( limitOffsetClauses )?
			{
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:114:7: ( orderClause )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0==ORDER) ) {
				alt16=1;
			}
			switch (alt16) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:114:7: orderClause
					{
					pushFollow(FOLLOW_orderClause_in_solutionModifier347);
					orderClause();
					state._fsp--;

					}
					break;

			}

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:114:20: ( limitOffsetClauses )?
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==LIMIT||LA17_0==OFFSET) ) {
				alt17=1;
			}
			switch (alt17) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:114:20: limitOffsetClauses
					{
					pushFollow(FOLLOW_limitOffsetClauses_in_solutionModifier350);
					limitOffsetClauses();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "solutionModifier"



	// $ANTLR start "limitOffsetClauses"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:117:1: limitOffsetClauses : ( limitClause ( offsetClause )? | offsetClause ( limitClause )? ) ;
	public final void limitOffsetClauses() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:118:5: ( ( limitClause ( offsetClause )? | offsetClause ( limitClause )? ) )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:118:7: ( limitClause ( offsetClause )? | offsetClause ( limitClause )? )
			{
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:118:7: ( limitClause ( offsetClause )? | offsetClause ( limitClause )? )
			int alt20=2;
			int LA20_0 = input.LA(1);
			if ( (LA20_0==LIMIT) ) {
				alt20=1;
			}
			else if ( (LA20_0==OFFSET) ) {
				alt20=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 20, 0, input);
				throw nvae;
			}

			switch (alt20) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:118:9: limitClause ( offsetClause )?
					{
					pushFollow(FOLLOW_limitClause_in_limitOffsetClauses370);
					limitClause();
					state._fsp--;

					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:118:21: ( offsetClause )?
					int alt18=2;
					int LA18_0 = input.LA(1);
					if ( (LA18_0==OFFSET) ) {
						alt18=1;
					}
					switch (alt18) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:118:21: offsetClause
							{
							pushFollow(FOLLOW_offsetClause_in_limitOffsetClauses372);
							offsetClause();
							state._fsp--;

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:118:37: offsetClause ( limitClause )?
					{
					pushFollow(FOLLOW_offsetClause_in_limitOffsetClauses377);
					offsetClause();
					state._fsp--;

					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:118:50: ( limitClause )?
					int alt19=2;
					int LA19_0 = input.LA(1);
					if ( (LA19_0==LIMIT) ) {
						alt19=1;
					}
					switch (alt19) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:118:50: limitClause
							{
							pushFollow(FOLLOW_limitClause_in_limitOffsetClauses379);
							limitClause();
							state._fsp--;

							}
							break;

					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "limitOffsetClauses"



	// $ANTLR start "orderClause"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:121:1: orderClause : ORDER BY ( orderCondition )+ ;
	public final void orderClause() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:122:5: ( ORDER BY ( orderCondition )+ )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:122:7: ORDER BY ( orderCondition )+
			{
			match(input,ORDER,FOLLOW_ORDER_in_orderClause399); 
			match(input,BY,FOLLOW_BY_in_orderClause401); 
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:122:16: ( orderCondition )+
			int cnt21=0;
			loop21:
			while (true) {
				int alt21=2;
				int LA21_0 = input.LA(1);
				if ( (LA21_0==ASC||LA21_0==BOUND||LA21_0==DATATYPE||LA21_0==DESC||(LA21_0 >= IRI_REF && LA21_0 <= LANGMATCHES)||LA21_0==OPEN_BRACE||(LA21_0 >= PNAME_LN && LA21_0 <= PNAME_NS)||(LA21_0 >= REGEX && LA21_0 <= SAMETERM)||LA21_0==STR||(LA21_0 >= VAR1 && LA21_0 <= VAR2)) ) {
					alt21=1;
				}

				switch (alt21) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:122:16: orderCondition
					{
					pushFollow(FOLLOW_orderCondition_in_orderClause403);
					orderCondition();
					state._fsp--;

					}
					break;

				default :
					if ( cnt21 >= 1 ) break loop21;
					EarlyExitException eee = new EarlyExitException(21, input);
					throw eee;
				}
				cnt21++;
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "orderClause"



	// $ANTLR start "orderCondition"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:125:1: orderCondition : ( ( ( ASC | DESC ) brackettedExpression ) | ( constraint | var ) );
	public final void orderCondition() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:126:5: ( ( ( ASC | DESC ) brackettedExpression ) | ( constraint | var ) )
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( (LA23_0==ASC||LA23_0==DESC) ) {
				alt23=1;
			}
			else if ( (LA23_0==BOUND||LA23_0==DATATYPE||(LA23_0 >= IRI_REF && LA23_0 <= LANGMATCHES)||LA23_0==OPEN_BRACE||(LA23_0 >= PNAME_LN && LA23_0 <= PNAME_NS)||(LA23_0 >= REGEX && LA23_0 <= SAMETERM)||LA23_0==STR||(LA23_0 >= VAR1 && LA23_0 <= VAR2)) ) {
				alt23=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 23, 0, input);
				throw nvae;
			}

			switch (alt23) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:126:7: ( ( ASC | DESC ) brackettedExpression )
					{
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:126:7: ( ( ASC | DESC ) brackettedExpression )
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:126:9: ( ASC | DESC ) brackettedExpression
					{
					if ( input.LA(1)==ASC||input.LA(1)==DESC ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_brackettedExpression_in_orderCondition433);
					brackettedExpression();
					state._fsp--;

					}

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:127:7: ( constraint | var )
					{
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:127:7: ( constraint | var )
					int alt22=2;
					int LA22_0 = input.LA(1);
					if ( (LA22_0==BOUND||LA22_0==DATATYPE||(LA22_0 >= IRI_REF && LA22_0 <= LANGMATCHES)||LA22_0==OPEN_BRACE||(LA22_0 >= PNAME_LN && LA22_0 <= PNAME_NS)||(LA22_0 >= REGEX && LA22_0 <= SAMETERM)||LA22_0==STR) ) {
						alt22=1;
					}
					else if ( ((LA22_0 >= VAR1 && LA22_0 <= VAR2)) ) {
						alt22=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 22, 0, input);
						throw nvae;
					}

					switch (alt22) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:127:9: constraint
							{
							pushFollow(FOLLOW_constraint_in_orderCondition445);
							constraint();
							state._fsp--;

							}
							break;
						case 2 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:127:22: var
							{
							pushFollow(FOLLOW_var_in_orderCondition449);
							var();
							state._fsp--;

							}
							break;

					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "orderCondition"



	// $ANTLR start "limitClause"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:130:1: limitClause : LIMIT INTEGER ;
	public final void limitClause() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:131:5: ( LIMIT INTEGER )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:131:7: LIMIT INTEGER
			{
			match(input,LIMIT,FOLLOW_LIMIT_in_limitClause468); 
			match(input,INTEGER,FOLLOW_INTEGER_in_limitClause470); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "limitClause"



	// $ANTLR start "offsetClause"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:134:1: offsetClause : OFFSET INTEGER ;
	public final void offsetClause() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:135:5: ( OFFSET INTEGER )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:135:7: OFFSET INTEGER
			{
			match(input,OFFSET,FOLLOW_OFFSET_in_offsetClause487); 
			match(input,INTEGER,FOLLOW_INTEGER_in_offsetClause489); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "offsetClause"



	// $ANTLR start "groupGraphPattern"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:138:1: groupGraphPattern : OPEN_CURLY_BRACE ( triplesBlock )? ( ( graphPatternNotTriples | filter ) ( DOT )? ( triplesBlock )? )* CLOSE_CURLY_BRACE ;
	public final void groupGraphPattern() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:5: ( OPEN_CURLY_BRACE ( triplesBlock )? ( ( graphPatternNotTriples | filter ) ( DOT )? ( triplesBlock )? )* CLOSE_CURLY_BRACE )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:7: OPEN_CURLY_BRACE ( triplesBlock )? ( ( graphPatternNotTriples | filter ) ( DOT )? ( triplesBlock )? )* CLOSE_CURLY_BRACE
			{
			match(input,OPEN_CURLY_BRACE,FOLLOW_OPEN_CURLY_BRACE_in_groupGraphPattern506); 
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:24: ( triplesBlock )?
			int alt24=2;
			int LA24_0 = input.LA(1);
			if ( (LA24_0==BLANK_NODE_LABEL||(LA24_0 >= DECIMAL && LA24_0 <= DECIMAL_POSITIVE)||(LA24_0 >= DOUBLE && LA24_0 <= DOUBLE_POSITIVE)||LA24_0==FALSE||(LA24_0 >= INTEGER && LA24_0 <= IRI_REF)||LA24_0==OPEN_BRACE||LA24_0==OPEN_SQUARE_BRACE||(LA24_0 >= PNAME_LN && LA24_0 <= PNAME_NS)||(LA24_0 >= STRING_LITERAL1 && LA24_0 <= TRUE)||(LA24_0 >= VAR1 && LA24_0 <= VAR2)) ) {
				alt24=1;
			}
			switch (alt24) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:24: triplesBlock
					{
					pushFollow(FOLLOW_triplesBlock_in_groupGraphPattern508);
					triplesBlock();
					state._fsp--;

					}
					break;

			}

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:38: ( ( graphPatternNotTriples | filter ) ( DOT )? ( triplesBlock )? )*
			loop28:
			while (true) {
				int alt28=2;
				int LA28_0 = input.LA(1);
				if ( (LA28_0==FILTER||LA28_0==GRAPH||LA28_0==OPEN_CURLY_BRACE||LA28_0==OPTIONAL) ) {
					alt28=1;
				}

				switch (alt28) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:40: ( graphPatternNotTriples | filter ) ( DOT )? ( triplesBlock )?
					{
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:40: ( graphPatternNotTriples | filter )
					int alt25=2;
					int LA25_0 = input.LA(1);
					if ( (LA25_0==GRAPH||LA25_0==OPEN_CURLY_BRACE||LA25_0==OPTIONAL) ) {
						alt25=1;
					}
					else if ( (LA25_0==FILTER) ) {
						alt25=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 25, 0, input);
						throw nvae;
					}

					switch (alt25) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:42: graphPatternNotTriples
							{
							pushFollow(FOLLOW_graphPatternNotTriples_in_groupGraphPattern515);
							graphPatternNotTriples();
							state._fsp--;

							}
							break;
						case 2 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:67: filter
							{
							pushFollow(FOLLOW_filter_in_groupGraphPattern519);
							filter();
							state._fsp--;

							}
							break;

					}

					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:76: ( DOT )?
					int alt26=2;
					int LA26_0 = input.LA(1);
					if ( (LA26_0==DOT) ) {
						alt26=1;
					}
					switch (alt26) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:76: DOT
							{
							match(input,DOT,FOLLOW_DOT_in_groupGraphPattern523); 
							}
							break;

					}

					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:81: ( triplesBlock )?
					int alt27=2;
					int LA27_0 = input.LA(1);
					if ( (LA27_0==BLANK_NODE_LABEL||(LA27_0 >= DECIMAL && LA27_0 <= DECIMAL_POSITIVE)||(LA27_0 >= DOUBLE && LA27_0 <= DOUBLE_POSITIVE)||LA27_0==FALSE||(LA27_0 >= INTEGER && LA27_0 <= IRI_REF)||LA27_0==OPEN_BRACE||LA27_0==OPEN_SQUARE_BRACE||(LA27_0 >= PNAME_LN && LA27_0 <= PNAME_NS)||(LA27_0 >= STRING_LITERAL1 && LA27_0 <= TRUE)||(LA27_0 >= VAR1 && LA27_0 <= VAR2)) ) {
						alt27=1;
					}
					switch (alt27) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:139:81: triplesBlock
							{
							pushFollow(FOLLOW_triplesBlock_in_groupGraphPattern526);
							triplesBlock();
							state._fsp--;

							}
							break;

					}

					}
					break;

				default :
					break loop28;
				}
			}

			match(input,CLOSE_CURLY_BRACE,FOLLOW_CLOSE_CURLY_BRACE_in_groupGraphPattern532); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "groupGraphPattern"



	// $ANTLR start "triplesBlock"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:142:1: triplesBlock : triplesSameSubject ( DOT ( triplesBlock )? )? ;
	public final void triplesBlock() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:143:5: ( triplesSameSubject ( DOT ( triplesBlock )? )? )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:143:7: triplesSameSubject ( DOT ( triplesBlock )? )?
			{
			pushFollow(FOLLOW_triplesSameSubject_in_triplesBlock549);
			triplesSameSubject();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:143:26: ( DOT ( triplesBlock )? )?
			int alt30=2;
			int LA30_0 = input.LA(1);
			if ( (LA30_0==DOT) ) {
				alt30=1;
			}
			switch (alt30) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:143:28: DOT ( triplesBlock )?
					{
					match(input,DOT,FOLLOW_DOT_in_triplesBlock553); 
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:143:32: ( triplesBlock )?
					int alt29=2;
					int LA29_0 = input.LA(1);
					if ( (LA29_0==BLANK_NODE_LABEL||(LA29_0 >= DECIMAL && LA29_0 <= DECIMAL_POSITIVE)||(LA29_0 >= DOUBLE && LA29_0 <= DOUBLE_POSITIVE)||LA29_0==FALSE||(LA29_0 >= INTEGER && LA29_0 <= IRI_REF)||LA29_0==OPEN_BRACE||LA29_0==OPEN_SQUARE_BRACE||(LA29_0 >= PNAME_LN && LA29_0 <= PNAME_NS)||(LA29_0 >= STRING_LITERAL1 && LA29_0 <= TRUE)||(LA29_0 >= VAR1 && LA29_0 <= VAR2)) ) {
						alt29=1;
					}
					switch (alt29) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:143:32: triplesBlock
							{
							pushFollow(FOLLOW_triplesBlock_in_triplesBlock555);
							triplesBlock();
							state._fsp--;

							}
							break;

					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "triplesBlock"



	// $ANTLR start "graphPatternNotTriples"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:146:1: graphPatternNotTriples : ( optionalGraphPattern | groupOrUnionGraphPattern | graphGraphPattern );
	public final void graphPatternNotTriples() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:147:5: ( optionalGraphPattern | groupOrUnionGraphPattern | graphGraphPattern )
			int alt31=3;
			switch ( input.LA(1) ) {
			case OPTIONAL:
				{
				alt31=1;
				}
				break;
			case OPEN_CURLY_BRACE:
				{
				alt31=2;
				}
				break;
			case GRAPH:
				{
				alt31=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 31, 0, input);
				throw nvae;
			}
			switch (alt31) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:147:7: optionalGraphPattern
					{
					pushFollow(FOLLOW_optionalGraphPattern_in_graphPatternNotTriples576);
					optionalGraphPattern();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:147:30: groupOrUnionGraphPattern
					{
					pushFollow(FOLLOW_groupOrUnionGraphPattern_in_graphPatternNotTriples580);
					groupOrUnionGraphPattern();
					state._fsp--;

					}
					break;
				case 3 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:147:57: graphGraphPattern
					{
					pushFollow(FOLLOW_graphGraphPattern_in_graphPatternNotTriples584);
					graphGraphPattern();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "graphPatternNotTriples"



	// $ANTLR start "optionalGraphPattern"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:150:1: optionalGraphPattern : OPTIONAL groupGraphPattern ;
	public final void optionalGraphPattern() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:151:5: ( OPTIONAL groupGraphPattern )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:151:7: OPTIONAL groupGraphPattern
			{
			match(input,OPTIONAL,FOLLOW_OPTIONAL_in_optionalGraphPattern601); 
			pushFollow(FOLLOW_groupGraphPattern_in_optionalGraphPattern603);
			groupGraphPattern();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "optionalGraphPattern"



	// $ANTLR start "graphGraphPattern"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:154:1: graphGraphPattern : GRAPH varOrIRIref groupGraphPattern ;
	public final void graphGraphPattern() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:155:5: ( GRAPH varOrIRIref groupGraphPattern )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:155:7: GRAPH varOrIRIref groupGraphPattern
			{
			match(input,GRAPH,FOLLOW_GRAPH_in_graphGraphPattern620); 
			pushFollow(FOLLOW_varOrIRIref_in_graphGraphPattern622);
			varOrIRIref();
			state._fsp--;

			pushFollow(FOLLOW_groupGraphPattern_in_graphGraphPattern624);
			groupGraphPattern();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "graphGraphPattern"



	// $ANTLR start "groupOrUnionGraphPattern"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:158:1: groupOrUnionGraphPattern : groupGraphPattern ( UNION groupGraphPattern )* ;
	public final void groupOrUnionGraphPattern() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:159:5: ( groupGraphPattern ( UNION groupGraphPattern )* )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:159:7: groupGraphPattern ( UNION groupGraphPattern )*
			{
			pushFollow(FOLLOW_groupGraphPattern_in_groupOrUnionGraphPattern641);
			groupGraphPattern();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:159:25: ( UNION groupGraphPattern )*
			loop32:
			while (true) {
				int alt32=2;
				int LA32_0 = input.LA(1);
				if ( (LA32_0==UNION) ) {
					alt32=1;
				}

				switch (alt32) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:159:27: UNION groupGraphPattern
					{
					match(input,UNION,FOLLOW_UNION_in_groupOrUnionGraphPattern645); 
					pushFollow(FOLLOW_groupGraphPattern_in_groupOrUnionGraphPattern647);
					groupGraphPattern();
					state._fsp--;

					}
					break;

				default :
					break loop32;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "groupOrUnionGraphPattern"



	// $ANTLR start "filter"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:162:1: filter : FILTER constraint ;
	public final void filter() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:163:5: ( FILTER constraint )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:163:7: FILTER constraint
			{
			match(input,FILTER,FOLLOW_FILTER_in_filter667); 
			pushFollow(FOLLOW_constraint_in_filter669);
			constraint();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "filter"



	// $ANTLR start "constraint"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:166:1: constraint : ( brackettedExpression | builtInCall | functionCall );
	public final void constraint() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:167:5: ( brackettedExpression | builtInCall | functionCall )
			int alt33=3;
			switch ( input.LA(1) ) {
			case OPEN_BRACE:
				{
				alt33=1;
				}
				break;
			case BOUND:
			case DATATYPE:
			case ISBLANK:
			case ISIRI:
			case ISLITERAL:
			case ISURI:
			case LANG:
			case LANGMATCHES:
			case REGEX:
			case SAMETERM:
			case STR:
				{
				alt33=2;
				}
				break;
			case IRI_REF:
			case PNAME_LN:
			case PNAME_NS:
				{
				alt33=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 33, 0, input);
				throw nvae;
			}
			switch (alt33) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:167:7: brackettedExpression
					{
					pushFollow(FOLLOW_brackettedExpression_in_constraint686);
					brackettedExpression();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:167:30: builtInCall
					{
					pushFollow(FOLLOW_builtInCall_in_constraint690);
					builtInCall();
					state._fsp--;

					}
					break;
				case 3 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:167:44: functionCall
					{
					pushFollow(FOLLOW_functionCall_in_constraint694);
					functionCall();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "constraint"



	// $ANTLR start "functionCall"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:170:1: functionCall : iriRef argList ;
	public final void functionCall() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:171:5: ( iriRef argList )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:171:7: iriRef argList
			{
			pushFollow(FOLLOW_iriRef_in_functionCall711);
			iriRef();
			state._fsp--;

			pushFollow(FOLLOW_argList_in_functionCall713);
			argList();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "functionCall"



	// $ANTLR start "argList"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:174:1: argList : ( OPEN_BRACE CLOSE_BRACE | OPEN_BRACE expression ( COMMA expression )* CLOSE_BRACE ) ;
	public final void argList() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:175:5: ( ( OPEN_BRACE CLOSE_BRACE | OPEN_BRACE expression ( COMMA expression )* CLOSE_BRACE ) )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:175:7: ( OPEN_BRACE CLOSE_BRACE | OPEN_BRACE expression ( COMMA expression )* CLOSE_BRACE )
			{
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:175:7: ( OPEN_BRACE CLOSE_BRACE | OPEN_BRACE expression ( COMMA expression )* CLOSE_BRACE )
			int alt35=2;
			int LA35_0 = input.LA(1);
			if ( (LA35_0==OPEN_BRACE) ) {
				int LA35_1 = input.LA(2);
				if ( (LA35_1==CLOSE_BRACE) ) {
					alt35=1;
				}
				else if ( (LA35_1==BOUND||(LA35_1 >= DATATYPE && LA35_1 <= DECIMAL_POSITIVE)||(LA35_1 >= DOUBLE && LA35_1 <= DOUBLE_POSITIVE)||LA35_1==FALSE||(LA35_1 >= INTEGER && LA35_1 <= LANGMATCHES)||LA35_1==MINUS||LA35_1==NOT||LA35_1==OPEN_BRACE||(LA35_1 >= PLUS && LA35_1 <= PNAME_NS)||(LA35_1 >= REGEX && LA35_1 <= SAMETERM)||(LA35_1 >= STR && LA35_1 <= TRUE)||(LA35_1 >= VAR1 && LA35_1 <= VAR2)) ) {
					alt35=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 35, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 35, 0, input);
				throw nvae;
			}

			switch (alt35) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:175:9: OPEN_BRACE CLOSE_BRACE
					{
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_argList732); 
					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_argList734); 
					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:175:34: OPEN_BRACE expression ( COMMA expression )* CLOSE_BRACE
					{
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_argList738); 
					pushFollow(FOLLOW_expression_in_argList740);
					expression();
					state._fsp--;

					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:175:56: ( COMMA expression )*
					loop34:
					while (true) {
						int alt34=2;
						int LA34_0 = input.LA(1);
						if ( (LA34_0==COMMA) ) {
							alt34=1;
						}

						switch (alt34) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:175:58: COMMA expression
							{
							match(input,COMMA,FOLLOW_COMMA_in_argList744); 
							pushFollow(FOLLOW_expression_in_argList746);
							expression();
							state._fsp--;

							}
							break;

						default :
							break loop34;
						}
					}

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_argList751); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "argList"



	// $ANTLR start "constructTemplate"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:178:1: constructTemplate : OPEN_CURLY_BRACE ( constructTriples )? CLOSE_CURLY_BRACE ;
	public final void constructTemplate() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:179:5: ( OPEN_CURLY_BRACE ( constructTriples )? CLOSE_CURLY_BRACE )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:179:7: OPEN_CURLY_BRACE ( constructTriples )? CLOSE_CURLY_BRACE
			{
			match(input,OPEN_CURLY_BRACE,FOLLOW_OPEN_CURLY_BRACE_in_constructTemplate770); 
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:179:24: ( constructTriples )?
			int alt36=2;
			int LA36_0 = input.LA(1);
			if ( (LA36_0==BLANK_NODE_LABEL||(LA36_0 >= DECIMAL && LA36_0 <= DECIMAL_POSITIVE)||(LA36_0 >= DOUBLE && LA36_0 <= DOUBLE_POSITIVE)||LA36_0==FALSE||(LA36_0 >= INTEGER && LA36_0 <= IRI_REF)||LA36_0==OPEN_BRACE||LA36_0==OPEN_SQUARE_BRACE||(LA36_0 >= PNAME_LN && LA36_0 <= PNAME_NS)||(LA36_0 >= STRING_LITERAL1 && LA36_0 <= TRUE)||(LA36_0 >= VAR1 && LA36_0 <= VAR2)) ) {
				alt36=1;
			}
			switch (alt36) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:179:24: constructTriples
					{
					pushFollow(FOLLOW_constructTriples_in_constructTemplate772);
					constructTriples();
					state._fsp--;

					}
					break;

			}

			match(input,CLOSE_CURLY_BRACE,FOLLOW_CLOSE_CURLY_BRACE_in_constructTemplate775); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "constructTemplate"



	// $ANTLR start "constructTriples"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:182:1: constructTriples : triplesSameSubject ( DOT ( constructTriples )? )? ;
	public final void constructTriples() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:183:5: ( triplesSameSubject ( DOT ( constructTriples )? )? )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:183:7: triplesSameSubject ( DOT ( constructTriples )? )?
			{
			pushFollow(FOLLOW_triplesSameSubject_in_constructTriples792);
			triplesSameSubject();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:183:26: ( DOT ( constructTriples )? )?
			int alt38=2;
			int LA38_0 = input.LA(1);
			if ( (LA38_0==DOT) ) {
				alt38=1;
			}
			switch (alt38) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:183:28: DOT ( constructTriples )?
					{
					match(input,DOT,FOLLOW_DOT_in_constructTriples796); 
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:183:32: ( constructTriples )?
					int alt37=2;
					int LA37_0 = input.LA(1);
					if ( (LA37_0==BLANK_NODE_LABEL||(LA37_0 >= DECIMAL && LA37_0 <= DECIMAL_POSITIVE)||(LA37_0 >= DOUBLE && LA37_0 <= DOUBLE_POSITIVE)||LA37_0==FALSE||(LA37_0 >= INTEGER && LA37_0 <= IRI_REF)||LA37_0==OPEN_BRACE||LA37_0==OPEN_SQUARE_BRACE||(LA37_0 >= PNAME_LN && LA37_0 <= PNAME_NS)||(LA37_0 >= STRING_LITERAL1 && LA37_0 <= TRUE)||(LA37_0 >= VAR1 && LA37_0 <= VAR2)) ) {
						alt37=1;
					}
					switch (alt37) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:183:32: constructTriples
							{
							pushFollow(FOLLOW_constructTriples_in_constructTriples798);
							constructTriples();
							state._fsp--;

							}
							break;

					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "constructTriples"



	// $ANTLR start "triplesSameSubject"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:186:1: triplesSameSubject : ( varOrTerm propertyListNotEmpty | triplesNode propertyList );
	public final void triplesSameSubject() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:187:5: ( varOrTerm propertyListNotEmpty | triplesNode propertyList )
			int alt39=2;
			switch ( input.LA(1) ) {
			case BLANK_NODE_LABEL:
			case DECIMAL:
			case DECIMAL_NEGATIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE:
			case DOUBLE_NEGATIVE:
			case DOUBLE_POSITIVE:
			case FALSE:
			case INTEGER:
			case INTEGER_NEGATIVE:
			case INTEGER_POSITIVE:
			case IRI_REF:
			case PNAME_LN:
			case PNAME_NS:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
			case TRUE:
			case VAR1:
			case VAR2:
				{
				alt39=1;
				}
				break;
			case OPEN_SQUARE_BRACE:
				{
				int LA39_2 = input.LA(2);
				if ( (LA39_2==CLOSE_SQUARE_BRACE) ) {
					alt39=1;
				}
				else if ( (LA39_2==A||LA39_2==IRI_REF||(LA39_2 >= PNAME_LN && LA39_2 <= PNAME_NS)||(LA39_2 >= VAR1 && LA39_2 <= VAR2)) ) {
					alt39=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 39, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case OPEN_BRACE:
				{
				int LA39_3 = input.LA(2);
				if ( (LA39_3==CLOSE_BRACE) ) {
					alt39=1;
				}
				else if ( (LA39_3==BLANK_NODE_LABEL||(LA39_3 >= DECIMAL && LA39_3 <= DECIMAL_POSITIVE)||(LA39_3 >= DOUBLE && LA39_3 <= DOUBLE_POSITIVE)||LA39_3==FALSE||(LA39_3 >= INTEGER && LA39_3 <= IRI_REF)||LA39_3==OPEN_BRACE||LA39_3==OPEN_SQUARE_BRACE||(LA39_3 >= PNAME_LN && LA39_3 <= PNAME_NS)||(LA39_3 >= STRING_LITERAL1 && LA39_3 <= TRUE)||(LA39_3 >= VAR1 && LA39_3 <= VAR2)) ) {
					alt39=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 39, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 39, 0, input);
				throw nvae;
			}
			switch (alt39) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:187:7: varOrTerm propertyListNotEmpty
					{
					pushFollow(FOLLOW_varOrTerm_in_triplesSameSubject819);
					varOrTerm();
					state._fsp--;

					pushFollow(FOLLOW_propertyListNotEmpty_in_triplesSameSubject821);
					propertyListNotEmpty();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:187:40: triplesNode propertyList
					{
					pushFollow(FOLLOW_triplesNode_in_triplesSameSubject825);
					triplesNode();
					state._fsp--;

					pushFollow(FOLLOW_propertyList_in_triplesSameSubject827);
					propertyList();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "triplesSameSubject"



	// $ANTLR start "propertyListNotEmpty"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:190:1: propertyListNotEmpty : verb objectList ( SEMICOLON ( verb objectList )? )* ;
	public final void propertyListNotEmpty() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:191:5: ( verb objectList ( SEMICOLON ( verb objectList )? )* )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:191:7: verb objectList ( SEMICOLON ( verb objectList )? )*
			{
			pushFollow(FOLLOW_verb_in_propertyListNotEmpty844);
			verb();
			state._fsp--;

			pushFollow(FOLLOW_objectList_in_propertyListNotEmpty846);
			objectList();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:191:23: ( SEMICOLON ( verb objectList )? )*
			loop41:
			while (true) {
				int alt41=2;
				int LA41_0 = input.LA(1);
				if ( (LA41_0==SEMICOLON) ) {
					alt41=1;
				}

				switch (alt41) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:191:25: SEMICOLON ( verb objectList )?
					{
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_propertyListNotEmpty850); 
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:191:35: ( verb objectList )?
					int alt40=2;
					int LA40_0 = input.LA(1);
					if ( (LA40_0==A||LA40_0==IRI_REF||(LA40_0 >= PNAME_LN && LA40_0 <= PNAME_NS)||(LA40_0 >= VAR1 && LA40_0 <= VAR2)) ) {
						alt40=1;
					}
					switch (alt40) {
						case 1 :
							// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:191:37: verb objectList
							{
							pushFollow(FOLLOW_verb_in_propertyListNotEmpty854);
							verb();
							state._fsp--;

							pushFollow(FOLLOW_objectList_in_propertyListNotEmpty856);
							objectList();
							state._fsp--;

							}
							break;

					}

					}
					break;

				default :
					break loop41;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "propertyListNotEmpty"



	// $ANTLR start "propertyList"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:194:1: propertyList : ( propertyListNotEmpty )? ;
	public final void propertyList() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:195:5: ( ( propertyListNotEmpty )? )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:195:7: ( propertyListNotEmpty )?
			{
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:195:7: ( propertyListNotEmpty )?
			int alt42=2;
			int LA42_0 = input.LA(1);
			if ( (LA42_0==A||LA42_0==IRI_REF||(LA42_0 >= PNAME_LN && LA42_0 <= PNAME_NS)||(LA42_0 >= VAR1 && LA42_0 <= VAR2)) ) {
				alt42=1;
			}
			switch (alt42) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:195:7: propertyListNotEmpty
					{
					pushFollow(FOLLOW_propertyListNotEmpty_in_propertyList879);
					propertyListNotEmpty();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "propertyList"



	// $ANTLR start "objectList"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:198:1: objectList : object ( COMMA object )* ;
	public final void objectList() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:199:5: ( object ( COMMA object )* )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:199:7: object ( COMMA object )*
			{
			pushFollow(FOLLOW_object_in_objectList897);
			object();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:199:14: ( COMMA object )*
			loop43:
			while (true) {
				int alt43=2;
				int LA43_0 = input.LA(1);
				if ( (LA43_0==COMMA) ) {
					alt43=1;
				}

				switch (alt43) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:199:16: COMMA object
					{
					match(input,COMMA,FOLLOW_COMMA_in_objectList901); 
					pushFollow(FOLLOW_object_in_objectList903);
					object();
					state._fsp--;

					}
					break;

				default :
					break loop43;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "objectList"



	// $ANTLR start "object"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:202:1: object : graphNode ;
	public final void object() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:203:5: ( graphNode )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:203:7: graphNode
			{
			pushFollow(FOLLOW_graphNode_in_object923);
			graphNode();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "object"



	// $ANTLR start "verb"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:206:1: verb : ( varOrIRIref | A );
	public final void verb() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:207:5: ( varOrIRIref | A )
			int alt44=2;
			int LA44_0 = input.LA(1);
			if ( (LA44_0==IRI_REF||(LA44_0 >= PNAME_LN && LA44_0 <= PNAME_NS)||(LA44_0 >= VAR1 && LA44_0 <= VAR2)) ) {
				alt44=1;
			}
			else if ( (LA44_0==A) ) {
				alt44=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 44, 0, input);
				throw nvae;
			}

			switch (alt44) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:207:7: varOrIRIref
					{
					pushFollow(FOLLOW_varOrIRIref_in_verb940);
					varOrIRIref();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:208:7: A
					{
					match(input,A,FOLLOW_A_in_verb948); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "verb"



	// $ANTLR start "triplesNode"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:211:1: triplesNode : ( collection | blankNodePropertyList );
	public final void triplesNode() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:212:5: ( collection | blankNodePropertyList )
			int alt45=2;
			int LA45_0 = input.LA(1);
			if ( (LA45_0==OPEN_BRACE) ) {
				alt45=1;
			}
			else if ( (LA45_0==OPEN_SQUARE_BRACE) ) {
				alt45=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 45, 0, input);
				throw nvae;
			}

			switch (alt45) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:212:7: collection
					{
					pushFollow(FOLLOW_collection_in_triplesNode965);
					collection();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:213:7: blankNodePropertyList
					{
					pushFollow(FOLLOW_blankNodePropertyList_in_triplesNode973);
					blankNodePropertyList();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "triplesNode"



	// $ANTLR start "blankNodePropertyList"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:216:1: blankNodePropertyList : OPEN_SQUARE_BRACE propertyListNotEmpty CLOSE_SQUARE_BRACE ;
	public final void blankNodePropertyList() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:217:5: ( OPEN_SQUARE_BRACE propertyListNotEmpty CLOSE_SQUARE_BRACE )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:217:7: OPEN_SQUARE_BRACE propertyListNotEmpty CLOSE_SQUARE_BRACE
			{
			match(input,OPEN_SQUARE_BRACE,FOLLOW_OPEN_SQUARE_BRACE_in_blankNodePropertyList990); 
			pushFollow(FOLLOW_propertyListNotEmpty_in_blankNodePropertyList992);
			propertyListNotEmpty();
			state._fsp--;

			match(input,CLOSE_SQUARE_BRACE,FOLLOW_CLOSE_SQUARE_BRACE_in_blankNodePropertyList994); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "blankNodePropertyList"



	// $ANTLR start "collection"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:220:1: collection : OPEN_BRACE ( graphNode )+ CLOSE_BRACE ;
	public final void collection() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:221:5: ( OPEN_BRACE ( graphNode )+ CLOSE_BRACE )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:221:7: OPEN_BRACE ( graphNode )+ CLOSE_BRACE
			{
			match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_collection1011); 
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:221:18: ( graphNode )+
			int cnt46=0;
			loop46:
			while (true) {
				int alt46=2;
				int LA46_0 = input.LA(1);
				if ( (LA46_0==BLANK_NODE_LABEL||(LA46_0 >= DECIMAL && LA46_0 <= DECIMAL_POSITIVE)||(LA46_0 >= DOUBLE && LA46_0 <= DOUBLE_POSITIVE)||LA46_0==FALSE||(LA46_0 >= INTEGER && LA46_0 <= IRI_REF)||LA46_0==OPEN_BRACE||LA46_0==OPEN_SQUARE_BRACE||(LA46_0 >= PNAME_LN && LA46_0 <= PNAME_NS)||(LA46_0 >= STRING_LITERAL1 && LA46_0 <= TRUE)||(LA46_0 >= VAR1 && LA46_0 <= VAR2)) ) {
					alt46=1;
				}

				switch (alt46) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:221:18: graphNode
					{
					pushFollow(FOLLOW_graphNode_in_collection1013);
					graphNode();
					state._fsp--;

					}
					break;

				default :
					if ( cnt46 >= 1 ) break loop46;
					EarlyExitException eee = new EarlyExitException(46, input);
					throw eee;
				}
				cnt46++;
			}

			match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_collection1016); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "collection"



	// $ANTLR start "graphNode"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:224:1: graphNode : ( varOrTerm | triplesNode );
	public final void graphNode() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:225:5: ( varOrTerm | triplesNode )
			int alt47=2;
			switch ( input.LA(1) ) {
			case BLANK_NODE_LABEL:
			case DECIMAL:
			case DECIMAL_NEGATIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE:
			case DOUBLE_NEGATIVE:
			case DOUBLE_POSITIVE:
			case FALSE:
			case INTEGER:
			case INTEGER_NEGATIVE:
			case INTEGER_POSITIVE:
			case IRI_REF:
			case PNAME_LN:
			case PNAME_NS:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
			case TRUE:
			case VAR1:
			case VAR2:
				{
				alt47=1;
				}
				break;
			case OPEN_SQUARE_BRACE:
				{
				int LA47_2 = input.LA(2);
				if ( (LA47_2==CLOSE_SQUARE_BRACE) ) {
					alt47=1;
				}
				else if ( (LA47_2==A||LA47_2==IRI_REF||(LA47_2 >= PNAME_LN && LA47_2 <= PNAME_NS)||(LA47_2 >= VAR1 && LA47_2 <= VAR2)) ) {
					alt47=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 47, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case OPEN_BRACE:
				{
				int LA47_3 = input.LA(2);
				if ( (LA47_3==CLOSE_BRACE) ) {
					alt47=1;
				}
				else if ( (LA47_3==BLANK_NODE_LABEL||(LA47_3 >= DECIMAL && LA47_3 <= DECIMAL_POSITIVE)||(LA47_3 >= DOUBLE && LA47_3 <= DOUBLE_POSITIVE)||LA47_3==FALSE||(LA47_3 >= INTEGER && LA47_3 <= IRI_REF)||LA47_3==OPEN_BRACE||LA47_3==OPEN_SQUARE_BRACE||(LA47_3 >= PNAME_LN && LA47_3 <= PNAME_NS)||(LA47_3 >= STRING_LITERAL1 && LA47_3 <= TRUE)||(LA47_3 >= VAR1 && LA47_3 <= VAR2)) ) {
					alt47=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 47, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 47, 0, input);
				throw nvae;
			}
			switch (alt47) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:225:7: varOrTerm
					{
					pushFollow(FOLLOW_varOrTerm_in_graphNode1033);
					varOrTerm();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:225:19: triplesNode
					{
					pushFollow(FOLLOW_triplesNode_in_graphNode1037);
					triplesNode();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "graphNode"



	// $ANTLR start "varOrTerm"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:228:1: varOrTerm : ( var | graphTerm );
	public final void varOrTerm() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:229:5: ( var | graphTerm )
			int alt48=2;
			int LA48_0 = input.LA(1);
			if ( ((LA48_0 >= VAR1 && LA48_0 <= VAR2)) ) {
				alt48=1;
			}
			else if ( (LA48_0==BLANK_NODE_LABEL||(LA48_0 >= DECIMAL && LA48_0 <= DECIMAL_POSITIVE)||(LA48_0 >= DOUBLE && LA48_0 <= DOUBLE_POSITIVE)||LA48_0==FALSE||(LA48_0 >= INTEGER && LA48_0 <= IRI_REF)||LA48_0==OPEN_BRACE||LA48_0==OPEN_SQUARE_BRACE||(LA48_0 >= PNAME_LN && LA48_0 <= PNAME_NS)||(LA48_0 >= STRING_LITERAL1 && LA48_0 <= TRUE)) ) {
				alt48=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 48, 0, input);
				throw nvae;
			}

			switch (alt48) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:229:7: var
					{
					pushFollow(FOLLOW_var_in_varOrTerm1054);
					var();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:230:7: graphTerm
					{
					pushFollow(FOLLOW_graphTerm_in_varOrTerm1062);
					graphTerm();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "varOrTerm"



	// $ANTLR start "varOrIRIref"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:233:1: varOrIRIref : ( var | iriRef );
	public final void varOrIRIref() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:234:5: ( var | iriRef )
			int alt49=2;
			int LA49_0 = input.LA(1);
			if ( ((LA49_0 >= VAR1 && LA49_0 <= VAR2)) ) {
				alt49=1;
			}
			else if ( (LA49_0==IRI_REF||(LA49_0 >= PNAME_LN && LA49_0 <= PNAME_NS)) ) {
				alt49=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 49, 0, input);
				throw nvae;
			}

			switch (alt49) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:234:7: var
					{
					pushFollow(FOLLOW_var_in_varOrIRIref1079);
					var();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:234:13: iriRef
					{
					pushFollow(FOLLOW_iriRef_in_varOrIRIref1083);
					iriRef();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "varOrIRIref"



	// $ANTLR start "var"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:237:1: var : ( VAR1 | VAR2 );
	public final void var() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:238:5: ( VAR1 | VAR2 )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
			{
			if ( (input.LA(1) >= VAR1 && input.LA(1) <= VAR2) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "var"



	// $ANTLR start "graphTerm"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:242:1: graphTerm : ( iriRef | rdfLiteral | numericLiteral | booleanLiteral | blankNode | OPEN_BRACE CLOSE_BRACE );
	public final void graphTerm() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:243:5: ( iriRef | rdfLiteral | numericLiteral | booleanLiteral | blankNode | OPEN_BRACE CLOSE_BRACE )
			int alt50=6;
			switch ( input.LA(1) ) {
			case IRI_REF:
			case PNAME_LN:
			case PNAME_NS:
				{
				alt50=1;
				}
				break;
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				{
				alt50=2;
				}
				break;
			case DECIMAL:
			case DECIMAL_NEGATIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE:
			case DOUBLE_NEGATIVE:
			case DOUBLE_POSITIVE:
			case INTEGER:
			case INTEGER_NEGATIVE:
			case INTEGER_POSITIVE:
				{
				alt50=3;
				}
				break;
			case FALSE:
			case TRUE:
				{
				alt50=4;
				}
				break;
			case BLANK_NODE_LABEL:
			case OPEN_SQUARE_BRACE:
				{
				alt50=5;
				}
				break;
			case OPEN_BRACE:
				{
				alt50=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 50, 0, input);
				throw nvae;
			}
			switch (alt50) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:243:7: iriRef
					{
					pushFollow(FOLLOW_iriRef_in_graphTerm1125);
					iriRef();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:244:7: rdfLiteral
					{
					pushFollow(FOLLOW_rdfLiteral_in_graphTerm1133);
					rdfLiteral();
					state._fsp--;

					}
					break;
				case 3 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:245:7: numericLiteral
					{
					pushFollow(FOLLOW_numericLiteral_in_graphTerm1141);
					numericLiteral();
					state._fsp--;

					}
					break;
				case 4 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:246:7: booleanLiteral
					{
					pushFollow(FOLLOW_booleanLiteral_in_graphTerm1149);
					booleanLiteral();
					state._fsp--;

					}
					break;
				case 5 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:247:7: blankNode
					{
					pushFollow(FOLLOW_blankNode_in_graphTerm1157);
					blankNode();
					state._fsp--;

					}
					break;
				case 6 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:248:7: OPEN_BRACE CLOSE_BRACE
					{
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_graphTerm1165); 
					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_graphTerm1167); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "graphTerm"



	// $ANTLR start "expression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:251:1: expression : conditionalOrExpression ;
	public final void expression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:252:5: ( conditionalOrExpression )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:252:7: conditionalOrExpression
			{
			pushFollow(FOLLOW_conditionalOrExpression_in_expression1184);
			conditionalOrExpression();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "expression"



	// $ANTLR start "conditionalOrExpression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:255:1: conditionalOrExpression : conditionalAndExpression ( OR conditionalAndExpression )* ;
	public final void conditionalOrExpression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:256:5: ( conditionalAndExpression ( OR conditionalAndExpression )* )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:256:7: conditionalAndExpression ( OR conditionalAndExpression )*
			{
			pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression1201);
			conditionalAndExpression();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:256:32: ( OR conditionalAndExpression )*
			loop51:
			while (true) {
				int alt51=2;
				int LA51_0 = input.LA(1);
				if ( (LA51_0==OR) ) {
					alt51=1;
				}

				switch (alt51) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:256:34: OR conditionalAndExpression
					{
					match(input,OR,FOLLOW_OR_in_conditionalOrExpression1205); 
					pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression1207);
					conditionalAndExpression();
					state._fsp--;

					}
					break;

				default :
					break loop51;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "conditionalOrExpression"



	// $ANTLR start "conditionalAndExpression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:259:1: conditionalAndExpression : valueLogical ( AND valueLogical )* ;
	public final void conditionalAndExpression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:260:5: ( valueLogical ( AND valueLogical )* )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:260:7: valueLogical ( AND valueLogical )*
			{
			pushFollow(FOLLOW_valueLogical_in_conditionalAndExpression1227);
			valueLogical();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:260:20: ( AND valueLogical )*
			loop52:
			while (true) {
				int alt52=2;
				int LA52_0 = input.LA(1);
				if ( (LA52_0==AND) ) {
					alt52=1;
				}

				switch (alt52) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:260:22: AND valueLogical
					{
					match(input,AND,FOLLOW_AND_in_conditionalAndExpression1231); 
					pushFollow(FOLLOW_valueLogical_in_conditionalAndExpression1233);
					valueLogical();
					state._fsp--;

					}
					break;

				default :
					break loop52;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "conditionalAndExpression"



	// $ANTLR start "valueLogical"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:263:1: valueLogical : relationalExpression ;
	public final void valueLogical() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:264:5: ( relationalExpression )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:264:7: relationalExpression
			{
			pushFollow(FOLLOW_relationalExpression_in_valueLogical1253);
			relationalExpression();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "valueLogical"



	// $ANTLR start "relationalExpression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:267:1: relationalExpression : numericExpression ( EQUAL numericExpression | NOT_EQUAL numericExpression | LESS numericExpression | GREATER numericExpression | LESS_EQUAL numericExpression | GREATER_EQUAL numericExpression )? ;
	public final void relationalExpression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:268:5: ( numericExpression ( EQUAL numericExpression | NOT_EQUAL numericExpression | LESS numericExpression | GREATER numericExpression | LESS_EQUAL numericExpression | GREATER_EQUAL numericExpression )? )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:268:7: numericExpression ( EQUAL numericExpression | NOT_EQUAL numericExpression | LESS numericExpression | GREATER numericExpression | LESS_EQUAL numericExpression | GREATER_EQUAL numericExpression )?
			{
			pushFollow(FOLLOW_numericExpression_in_relationalExpression1270);
			numericExpression();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:268:25: ( EQUAL numericExpression | NOT_EQUAL numericExpression | LESS numericExpression | GREATER numericExpression | LESS_EQUAL numericExpression | GREATER_EQUAL numericExpression )?
			int alt53=7;
			switch ( input.LA(1) ) {
				case EQUAL:
					{
					alt53=1;
					}
					break;
				case NOT_EQUAL:
					{
					alt53=2;
					}
					break;
				case LESS:
					{
					alt53=3;
					}
					break;
				case GREATER:
					{
					alt53=4;
					}
					break;
				case LESS_EQUAL:
					{
					alt53=5;
					}
					break;
				case GREATER_EQUAL:
					{
					alt53=6;
					}
					break;
			}
			switch (alt53) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:268:27: EQUAL numericExpression
					{
					match(input,EQUAL,FOLLOW_EQUAL_in_relationalExpression1274); 
					pushFollow(FOLLOW_numericExpression_in_relationalExpression1276);
					numericExpression();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:268:53: NOT_EQUAL numericExpression
					{
					match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_relationalExpression1280); 
					pushFollow(FOLLOW_numericExpression_in_relationalExpression1282);
					numericExpression();
					state._fsp--;

					}
					break;
				case 3 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:268:83: LESS numericExpression
					{
					match(input,LESS,FOLLOW_LESS_in_relationalExpression1286); 
					pushFollow(FOLLOW_numericExpression_in_relationalExpression1288);
					numericExpression();
					state._fsp--;

					}
					break;
				case 4 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:268:108: GREATER numericExpression
					{
					match(input,GREATER,FOLLOW_GREATER_in_relationalExpression1292); 
					pushFollow(FOLLOW_numericExpression_in_relationalExpression1294);
					numericExpression();
					state._fsp--;

					}
					break;
				case 5 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:268:136: LESS_EQUAL numericExpression
					{
					match(input,LESS_EQUAL,FOLLOW_LESS_EQUAL_in_relationalExpression1298); 
					pushFollow(FOLLOW_numericExpression_in_relationalExpression1300);
					numericExpression();
					state._fsp--;

					}
					break;
				case 6 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:268:167: GREATER_EQUAL numericExpression
					{
					match(input,GREATER_EQUAL,FOLLOW_GREATER_EQUAL_in_relationalExpression1304); 
					pushFollow(FOLLOW_numericExpression_in_relationalExpression1306);
					numericExpression();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "relationalExpression"



	// $ANTLR start "numericExpression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:271:1: numericExpression : additiveExpression ;
	public final void numericExpression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:272:5: ( additiveExpression )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:272:7: additiveExpression
			{
			pushFollow(FOLLOW_additiveExpression_in_numericExpression1326);
			additiveExpression();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "numericExpression"



	// $ANTLR start "additiveExpression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:275:1: additiveExpression : multiplicativeExpression ( PLUS multiplicativeExpression | MINUS multiplicativeExpression | numericLiteralPositive | numericLiteralNegative )* ;
	public final void additiveExpression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:276:5: ( multiplicativeExpression ( PLUS multiplicativeExpression | MINUS multiplicativeExpression | numericLiteralPositive | numericLiteralNegative )* )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:276:7: multiplicativeExpression ( PLUS multiplicativeExpression | MINUS multiplicativeExpression | numericLiteralPositive | numericLiteralNegative )*
			{
			pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1343);
			multiplicativeExpression();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:276:32: ( PLUS multiplicativeExpression | MINUS multiplicativeExpression | numericLiteralPositive | numericLiteralNegative )*
			loop54:
			while (true) {
				int alt54=5;
				switch ( input.LA(1) ) {
				case PLUS:
					{
					alt54=1;
					}
					break;
				case MINUS:
					{
					alt54=2;
					}
					break;
				case DECIMAL_POSITIVE:
				case DOUBLE_POSITIVE:
				case INTEGER_POSITIVE:
					{
					alt54=3;
					}
					break;
				case DECIMAL_NEGATIVE:
				case DOUBLE_NEGATIVE:
				case INTEGER_NEGATIVE:
					{
					alt54=4;
					}
					break;
				}
				switch (alt54) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:276:34: PLUS multiplicativeExpression
					{
					match(input,PLUS,FOLLOW_PLUS_in_additiveExpression1347); 
					pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1349);
					multiplicativeExpression();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:276:66: MINUS multiplicativeExpression
					{
					match(input,MINUS,FOLLOW_MINUS_in_additiveExpression1353); 
					pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1355);
					multiplicativeExpression();
					state._fsp--;

					}
					break;
				case 3 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:276:99: numericLiteralPositive
					{
					pushFollow(FOLLOW_numericLiteralPositive_in_additiveExpression1359);
					numericLiteralPositive();
					state._fsp--;

					}
					break;
				case 4 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:276:124: numericLiteralNegative
					{
					pushFollow(FOLLOW_numericLiteralNegative_in_additiveExpression1363);
					numericLiteralNegative();
					state._fsp--;

					}
					break;

				default :
					break loop54;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "additiveExpression"



	// $ANTLR start "multiplicativeExpression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:279:1: multiplicativeExpression : unaryExpression ( ASTERISK unaryExpression | DIVIDE unaryExpression )* ;
	public final void multiplicativeExpression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:280:5: ( unaryExpression ( ASTERISK unaryExpression | DIVIDE unaryExpression )* )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:280:7: unaryExpression ( ASTERISK unaryExpression | DIVIDE unaryExpression )*
			{
			pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1383);
			unaryExpression();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:280:23: ( ASTERISK unaryExpression | DIVIDE unaryExpression )*
			loop55:
			while (true) {
				int alt55=3;
				int LA55_0 = input.LA(1);
				if ( (LA55_0==ASTERISK) ) {
					alt55=1;
				}
				else if ( (LA55_0==DIVIDE) ) {
					alt55=2;
				}

				switch (alt55) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:280:25: ASTERISK unaryExpression
					{
					match(input,ASTERISK,FOLLOW_ASTERISK_in_multiplicativeExpression1387); 
					pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1389);
					unaryExpression();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:280:52: DIVIDE unaryExpression
					{
					match(input,DIVIDE,FOLLOW_DIVIDE_in_multiplicativeExpression1393); 
					pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1395);
					unaryExpression();
					state._fsp--;

					}
					break;

				default :
					break loop55;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "multiplicativeExpression"



	// $ANTLR start "unaryExpression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:283:1: unaryExpression : ( NOT primaryExpression | PLUS primaryExpression | MINUS primaryExpression | primaryExpression );
	public final void unaryExpression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:284:5: ( NOT primaryExpression | PLUS primaryExpression | MINUS primaryExpression | primaryExpression )
			int alt56=4;
			switch ( input.LA(1) ) {
			case NOT:
				{
				alt56=1;
				}
				break;
			case PLUS:
				{
				alt56=2;
				}
				break;
			case MINUS:
				{
				alt56=3;
				}
				break;
			case BOUND:
			case DATATYPE:
			case DECIMAL:
			case DECIMAL_NEGATIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE:
			case DOUBLE_NEGATIVE:
			case DOUBLE_POSITIVE:
			case FALSE:
			case INTEGER:
			case INTEGER_NEGATIVE:
			case INTEGER_POSITIVE:
			case IRI_REF:
			case ISBLANK:
			case ISIRI:
			case ISLITERAL:
			case ISURI:
			case LANG:
			case LANGMATCHES:
			case OPEN_BRACE:
			case PNAME_LN:
			case PNAME_NS:
			case REGEX:
			case SAMETERM:
			case STR:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
			case TRUE:
			case VAR1:
			case VAR2:
				{
				alt56=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 56, 0, input);
				throw nvae;
			}
			switch (alt56) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:284:7: NOT primaryExpression
					{
					match(input,NOT,FOLLOW_NOT_in_unaryExpression1415); 
					pushFollow(FOLLOW_primaryExpression_in_unaryExpression1417);
					primaryExpression();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:285:7: PLUS primaryExpression
					{
					match(input,PLUS,FOLLOW_PLUS_in_unaryExpression1425); 
					pushFollow(FOLLOW_primaryExpression_in_unaryExpression1427);
					primaryExpression();
					state._fsp--;

					}
					break;
				case 3 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:286:7: MINUS primaryExpression
					{
					match(input,MINUS,FOLLOW_MINUS_in_unaryExpression1435); 
					pushFollow(FOLLOW_primaryExpression_in_unaryExpression1437);
					primaryExpression();
					state._fsp--;

					}
					break;
				case 4 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:287:7: primaryExpression
					{
					pushFollow(FOLLOW_primaryExpression_in_unaryExpression1445);
					primaryExpression();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "unaryExpression"



	// $ANTLR start "primaryExpression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:290:1: primaryExpression : ( brackettedExpression | builtInCall | iriRefOrFunction | rdfLiteral | numericLiteral | booleanLiteral | var );
	public final void primaryExpression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:291:5: ( brackettedExpression | builtInCall | iriRefOrFunction | rdfLiteral | numericLiteral | booleanLiteral | var )
			int alt57=7;
			switch ( input.LA(1) ) {
			case OPEN_BRACE:
				{
				alt57=1;
				}
				break;
			case BOUND:
			case DATATYPE:
			case ISBLANK:
			case ISIRI:
			case ISLITERAL:
			case ISURI:
			case LANG:
			case LANGMATCHES:
			case REGEX:
			case SAMETERM:
			case STR:
				{
				alt57=2;
				}
				break;
			case IRI_REF:
			case PNAME_LN:
			case PNAME_NS:
				{
				alt57=3;
				}
				break;
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				{
				alt57=4;
				}
				break;
			case DECIMAL:
			case DECIMAL_NEGATIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE:
			case DOUBLE_NEGATIVE:
			case DOUBLE_POSITIVE:
			case INTEGER:
			case INTEGER_NEGATIVE:
			case INTEGER_POSITIVE:
				{
				alt57=5;
				}
				break;
			case FALSE:
			case TRUE:
				{
				alt57=6;
				}
				break;
			case VAR1:
			case VAR2:
				{
				alt57=7;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 57, 0, input);
				throw nvae;
			}
			switch (alt57) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:291:7: brackettedExpression
					{
					pushFollow(FOLLOW_brackettedExpression_in_primaryExpression1462);
					brackettedExpression();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:291:30: builtInCall
					{
					pushFollow(FOLLOW_builtInCall_in_primaryExpression1466);
					builtInCall();
					state._fsp--;

					}
					break;
				case 3 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:291:44: iriRefOrFunction
					{
					pushFollow(FOLLOW_iriRefOrFunction_in_primaryExpression1470);
					iriRefOrFunction();
					state._fsp--;

					}
					break;
				case 4 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:291:63: rdfLiteral
					{
					pushFollow(FOLLOW_rdfLiteral_in_primaryExpression1474);
					rdfLiteral();
					state._fsp--;

					}
					break;
				case 5 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:291:76: numericLiteral
					{
					pushFollow(FOLLOW_numericLiteral_in_primaryExpression1478);
					numericLiteral();
					state._fsp--;

					}
					break;
				case 6 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:291:93: booleanLiteral
					{
					pushFollow(FOLLOW_booleanLiteral_in_primaryExpression1482);
					booleanLiteral();
					state._fsp--;

					}
					break;
				case 7 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:291:110: var
					{
					pushFollow(FOLLOW_var_in_primaryExpression1486);
					var();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "primaryExpression"



	// $ANTLR start "brackettedExpression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:294:1: brackettedExpression : OPEN_BRACE expression CLOSE_BRACE ;
	public final void brackettedExpression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:295:5: ( OPEN_BRACE expression CLOSE_BRACE )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:295:7: OPEN_BRACE expression CLOSE_BRACE
			{
			match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_brackettedExpression1503); 
			pushFollow(FOLLOW_expression_in_brackettedExpression1505);
			expression();
			state._fsp--;

			match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_brackettedExpression1507); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "brackettedExpression"



	// $ANTLR start "builtInCall"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:299:1: builtInCall : ( STR OPEN_BRACE expression CLOSE_BRACE | LANG OPEN_BRACE expression CLOSE_BRACE | LANGMATCHES OPEN_BRACE expression COMMA expression CLOSE_BRACE | DATATYPE OPEN_BRACE expression CLOSE_BRACE | BOUND OPEN_BRACE var CLOSE_BRACE | SAMETERM OPEN_BRACE expression COMMA expression CLOSE_BRACE | ISIRI OPEN_BRACE expression CLOSE_BRACE | ISURI OPEN_BRACE expression CLOSE_BRACE | ISBLANK OPEN_BRACE expression CLOSE_BRACE | ISLITERAL OPEN_BRACE expression CLOSE_BRACE | regexExpression );
	public final void builtInCall() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:300:5: ( STR OPEN_BRACE expression CLOSE_BRACE | LANG OPEN_BRACE expression CLOSE_BRACE | LANGMATCHES OPEN_BRACE expression COMMA expression CLOSE_BRACE | DATATYPE OPEN_BRACE expression CLOSE_BRACE | BOUND OPEN_BRACE var CLOSE_BRACE | SAMETERM OPEN_BRACE expression COMMA expression CLOSE_BRACE | ISIRI OPEN_BRACE expression CLOSE_BRACE | ISURI OPEN_BRACE expression CLOSE_BRACE | ISBLANK OPEN_BRACE expression CLOSE_BRACE | ISLITERAL OPEN_BRACE expression CLOSE_BRACE | regexExpression )
			int alt58=11;
			switch ( input.LA(1) ) {
			case STR:
				{
				alt58=1;
				}
				break;
			case LANG:
				{
				alt58=2;
				}
				break;
			case LANGMATCHES:
				{
				alt58=3;
				}
				break;
			case DATATYPE:
				{
				alt58=4;
				}
				break;
			case BOUND:
				{
				alt58=5;
				}
				break;
			case SAMETERM:
				{
				alt58=6;
				}
				break;
			case ISIRI:
				{
				alt58=7;
				}
				break;
			case ISURI:
				{
				alt58=8;
				}
				break;
			case ISBLANK:
				{
				alt58=9;
				}
				break;
			case ISLITERAL:
				{
				alt58=10;
				}
				break;
			case REGEX:
				{
				alt58=11;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 58, 0, input);
				throw nvae;
			}
			switch (alt58) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:300:7: STR OPEN_BRACE expression CLOSE_BRACE
					{
					match(input,STR,FOLLOW_STR_in_builtInCall1525); 
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_builtInCall1527); 
					pushFollow(FOLLOW_expression_in_builtInCall1529);
					expression();
					state._fsp--;

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_builtInCall1531); 
					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:301:7: LANG OPEN_BRACE expression CLOSE_BRACE
					{
					match(input,LANG,FOLLOW_LANG_in_builtInCall1539); 
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_builtInCall1541); 
					pushFollow(FOLLOW_expression_in_builtInCall1543);
					expression();
					state._fsp--;

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_builtInCall1545); 
					}
					break;
				case 3 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:302:7: LANGMATCHES OPEN_BRACE expression COMMA expression CLOSE_BRACE
					{
					match(input,LANGMATCHES,FOLLOW_LANGMATCHES_in_builtInCall1553); 
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_builtInCall1555); 
					pushFollow(FOLLOW_expression_in_builtInCall1557);
					expression();
					state._fsp--;

					match(input,COMMA,FOLLOW_COMMA_in_builtInCall1559); 
					pushFollow(FOLLOW_expression_in_builtInCall1561);
					expression();
					state._fsp--;

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_builtInCall1563); 
					}
					break;
				case 4 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:303:7: DATATYPE OPEN_BRACE expression CLOSE_BRACE
					{
					match(input,DATATYPE,FOLLOW_DATATYPE_in_builtInCall1571); 
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_builtInCall1573); 
					pushFollow(FOLLOW_expression_in_builtInCall1575);
					expression();
					state._fsp--;

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_builtInCall1577); 
					}
					break;
				case 5 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:304:7: BOUND OPEN_BRACE var CLOSE_BRACE
					{
					match(input,BOUND,FOLLOW_BOUND_in_builtInCall1585); 
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_builtInCall1587); 
					pushFollow(FOLLOW_var_in_builtInCall1589);
					var();
					state._fsp--;

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_builtInCall1591); 
					}
					break;
				case 6 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:305:7: SAMETERM OPEN_BRACE expression COMMA expression CLOSE_BRACE
					{
					match(input,SAMETERM,FOLLOW_SAMETERM_in_builtInCall1599); 
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_builtInCall1601); 
					pushFollow(FOLLOW_expression_in_builtInCall1603);
					expression();
					state._fsp--;

					match(input,COMMA,FOLLOW_COMMA_in_builtInCall1605); 
					pushFollow(FOLLOW_expression_in_builtInCall1607);
					expression();
					state._fsp--;

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_builtInCall1609); 
					}
					break;
				case 7 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:306:7: ISIRI OPEN_BRACE expression CLOSE_BRACE
					{
					match(input,ISIRI,FOLLOW_ISIRI_in_builtInCall1617); 
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_builtInCall1619); 
					pushFollow(FOLLOW_expression_in_builtInCall1621);
					expression();
					state._fsp--;

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_builtInCall1623); 
					}
					break;
				case 8 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:307:7: ISURI OPEN_BRACE expression CLOSE_BRACE
					{
					match(input,ISURI,FOLLOW_ISURI_in_builtInCall1631); 
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_builtInCall1633); 
					pushFollow(FOLLOW_expression_in_builtInCall1635);
					expression();
					state._fsp--;

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_builtInCall1637); 
					}
					break;
				case 9 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:308:7: ISBLANK OPEN_BRACE expression CLOSE_BRACE
					{
					match(input,ISBLANK,FOLLOW_ISBLANK_in_builtInCall1645); 
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_builtInCall1647); 
					pushFollow(FOLLOW_expression_in_builtInCall1649);
					expression();
					state._fsp--;

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_builtInCall1651); 
					}
					break;
				case 10 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:309:7: ISLITERAL OPEN_BRACE expression CLOSE_BRACE
					{
					match(input,ISLITERAL,FOLLOW_ISLITERAL_in_builtInCall1659); 
					match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_builtInCall1661); 
					pushFollow(FOLLOW_expression_in_builtInCall1663);
					expression();
					state._fsp--;

					match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_builtInCall1665); 
					}
					break;
				case 11 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:310:7: regexExpression
					{
					pushFollow(FOLLOW_regexExpression_in_builtInCall1673);
					regexExpression();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "builtInCall"



	// $ANTLR start "regexExpression"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:313:1: regexExpression : REGEX OPEN_BRACE expression COMMA expression ( COMMA expression )? CLOSE_BRACE ;
	public final void regexExpression() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:314:5: ( REGEX OPEN_BRACE expression COMMA expression ( COMMA expression )? CLOSE_BRACE )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:314:7: REGEX OPEN_BRACE expression COMMA expression ( COMMA expression )? CLOSE_BRACE
			{
			match(input,REGEX,FOLLOW_REGEX_in_regexExpression1690); 
			match(input,OPEN_BRACE,FOLLOW_OPEN_BRACE_in_regexExpression1692); 
			pushFollow(FOLLOW_expression_in_regexExpression1694);
			expression();
			state._fsp--;

			match(input,COMMA,FOLLOW_COMMA_in_regexExpression1696); 
			pushFollow(FOLLOW_expression_in_regexExpression1698);
			expression();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:314:52: ( COMMA expression )?
			int alt59=2;
			int LA59_0 = input.LA(1);
			if ( (LA59_0==COMMA) ) {
				alt59=1;
			}
			switch (alt59) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:314:54: COMMA expression
					{
					match(input,COMMA,FOLLOW_COMMA_in_regexExpression1702); 
					pushFollow(FOLLOW_expression_in_regexExpression1704);
					expression();
					state._fsp--;

					}
					break;

			}

			match(input,CLOSE_BRACE,FOLLOW_CLOSE_BRACE_in_regexExpression1709); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "regexExpression"



	// $ANTLR start "iriRefOrFunction"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:317:1: iriRefOrFunction : iriRef ( argList )? ;
	public final void iriRefOrFunction() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:318:5: ( iriRef ( argList )? )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:318:7: iriRef ( argList )?
			{
			pushFollow(FOLLOW_iriRef_in_iriRefOrFunction1726);
			iriRef();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:318:14: ( argList )?
			int alt60=2;
			int LA60_0 = input.LA(1);
			if ( (LA60_0==OPEN_BRACE) ) {
				alt60=1;
			}
			switch (alt60) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:318:14: argList
					{
					pushFollow(FOLLOW_argList_in_iriRefOrFunction1728);
					argList();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "iriRefOrFunction"



	// $ANTLR start "rdfLiteral"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:321:1: rdfLiteral : string ( LANGTAG | ( REFERENCE iriRef ) )? ;
	public final void rdfLiteral() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:322:5: ( string ( LANGTAG | ( REFERENCE iriRef ) )? )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:322:7: string ( LANGTAG | ( REFERENCE iriRef ) )?
			{
			pushFollow(FOLLOW_string_in_rdfLiteral1746);
			string();
			state._fsp--;

			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:322:14: ( LANGTAG | ( REFERENCE iriRef ) )?
			int alt61=3;
			int LA61_0 = input.LA(1);
			if ( (LA61_0==LANGTAG) ) {
				alt61=1;
			}
			else if ( (LA61_0==REFERENCE) ) {
				alt61=2;
			}
			switch (alt61) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:322:16: LANGTAG
					{
					match(input,LANGTAG,FOLLOW_LANGTAG_in_rdfLiteral1750); 
					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:322:26: ( REFERENCE iriRef )
					{
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:322:26: ( REFERENCE iriRef )
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:322:28: REFERENCE iriRef
					{
					match(input,REFERENCE,FOLLOW_REFERENCE_in_rdfLiteral1756); 
					pushFollow(FOLLOW_iriRef_in_rdfLiteral1758);
					iriRef();
					state._fsp--;

					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "rdfLiteral"



	// $ANTLR start "numericLiteral"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:325:1: numericLiteral : ( numericLiteralUnsigned | numericLiteralPositive | numericLiteralNegative );
	public final void numericLiteral() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:326:5: ( numericLiteralUnsigned | numericLiteralPositive | numericLiteralNegative )
			int alt62=3;
			switch ( input.LA(1) ) {
			case DECIMAL:
			case DOUBLE:
			case INTEGER:
				{
				alt62=1;
				}
				break;
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_POSITIVE:
				{
				alt62=2;
				}
				break;
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case INTEGER_NEGATIVE:
				{
				alt62=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 62, 0, input);
				throw nvae;
			}
			switch (alt62) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:326:7: numericLiteralUnsigned
					{
					pushFollow(FOLLOW_numericLiteralUnsigned_in_numericLiteral1780);
					numericLiteralUnsigned();
					state._fsp--;

					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:326:32: numericLiteralPositive
					{
					pushFollow(FOLLOW_numericLiteralPositive_in_numericLiteral1784);
					numericLiteralPositive();
					state._fsp--;

					}
					break;
				case 3 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:326:57: numericLiteralNegative
					{
					pushFollow(FOLLOW_numericLiteralNegative_in_numericLiteral1788);
					numericLiteralNegative();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "numericLiteral"



	// $ANTLR start "numericLiteralUnsigned"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:329:1: numericLiteralUnsigned : ( INTEGER | DECIMAL | DOUBLE );
	public final void numericLiteralUnsigned() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:330:5: ( INTEGER | DECIMAL | DOUBLE )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
			{
			if ( input.LA(1)==DECIMAL||input.LA(1)==DOUBLE||input.LA(1)==INTEGER ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "numericLiteralUnsigned"



	// $ANTLR start "numericLiteralPositive"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:335:1: numericLiteralPositive : ( INTEGER_POSITIVE | DECIMAL_POSITIVE | DOUBLE_POSITIVE );
	public final void numericLiteralPositive() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:336:5: ( INTEGER_POSITIVE | DECIMAL_POSITIVE | DOUBLE_POSITIVE )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
			{
			if ( input.LA(1)==DECIMAL_POSITIVE||input.LA(1)==DOUBLE_POSITIVE||input.LA(1)==INTEGER_POSITIVE ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "numericLiteralPositive"



	// $ANTLR start "numericLiteralNegative"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:341:1: numericLiteralNegative : ( INTEGER_NEGATIVE | DECIMAL_NEGATIVE | DOUBLE_NEGATIVE );
	public final void numericLiteralNegative() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:342:5: ( INTEGER_NEGATIVE | DECIMAL_NEGATIVE | DOUBLE_NEGATIVE )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
			{
			if ( input.LA(1)==DECIMAL_NEGATIVE||input.LA(1)==DOUBLE_NEGATIVE||input.LA(1)==INTEGER_NEGATIVE ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "numericLiteralNegative"



	// $ANTLR start "booleanLiteral"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:347:1: booleanLiteral : ( TRUE | FALSE );
	public final void booleanLiteral() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:348:5: ( TRUE | FALSE )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
			{
			if ( input.LA(1)==FALSE||input.LA(1)==TRUE ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "booleanLiteral"



	// $ANTLR start "string"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:352:1: string : ( STRING_LITERAL1 | STRING_LITERAL2 | STRING_LITERAL_LONG1 | STRING_LITERAL_LONG2 );
	public final void string() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:353:5: ( STRING_LITERAL1 | STRING_LITERAL2 | STRING_LITERAL_LONG1 | STRING_LITERAL_LONG2 )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
			{
			if ( (input.LA(1) >= STRING_LITERAL1 && input.LA(1) <= STRING_LITERAL_LONG2) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "string"



	// $ANTLR start "iriRef"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:359:1: iriRef : ( IRI_REF | prefixedName );
	public final void iriRef() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:360:5: ( IRI_REF | prefixedName )
			int alt63=2;
			int LA63_0 = input.LA(1);
			if ( (LA63_0==IRI_REF) ) {
				alt63=1;
			}
			else if ( ((LA63_0 >= PNAME_LN && LA63_0 <= PNAME_NS)) ) {
				alt63=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 63, 0, input);
				throw nvae;
			}

			switch (alt63) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:360:7: IRI_REF
					{
					match(input,IRI_REF,FOLLOW_IRI_REF_in_iriRef1970); 
					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:361:7: prefixedName
					{
					pushFollow(FOLLOW_prefixedName_in_iriRef1978);
					prefixedName();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "iriRef"



	// $ANTLR start "prefixedName"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:364:1: prefixedName : ( PNAME_LN | PNAME_NS );
	public final void prefixedName() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:365:5: ( PNAME_LN | PNAME_NS )
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
			{
			if ( (input.LA(1) >= PNAME_LN && input.LA(1) <= PNAME_NS) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "prefixedName"



	// $ANTLR start "blankNode"
	// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:369:1: blankNode : ( BLANK_NODE_LABEL | OPEN_SQUARE_BRACE CLOSE_SQUARE_BRACE );
	public final void blankNode() throws RecognitionException {
		try {
			// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:370:5: ( BLANK_NODE_LABEL | OPEN_SQUARE_BRACE CLOSE_SQUARE_BRACE )
			int alt64=2;
			int LA64_0 = input.LA(1);
			if ( (LA64_0==BLANK_NODE_LABEL) ) {
				alt64=1;
			}
			else if ( (LA64_0==OPEN_SQUARE_BRACE) ) {
				alt64=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 64, 0, input);
				throw nvae;
			}

			switch (alt64) {
				case 1 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:370:7: BLANK_NODE_LABEL
					{
					match(input,BLANK_NODE_LABEL,FOLLOW_BLANK_NODE_LABEL_in_blankNode2020); 
					}
					break;
				case 2 :
					// /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:371:7: OPEN_SQUARE_BRACE CLOSE_SQUARE_BRACE
					{
					match(input,OPEN_SQUARE_BRACE,FOLLOW_OPEN_SQUARE_BRACE_in_blankNode2028); 
					match(input,CLOSE_SQUARE_BRACE,FOLLOW_CLOSE_SQUARE_BRACE_in_blankNode2030); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "blankNode"

	// Delegated rules



	public static final BitSet FOLLOW_prologue_in_query19 = new BitSet(new long[]{0x0000000002080100L,0x0000000000020000L});
	public static final BitSet FOLLOW_selectQuery_in_query23 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_constructQuery_in_query27 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_describeQuery_in_query31 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_askQuery_in_query35 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_query39 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_baseDecl_in_prologue56 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
	public static final BitSet FOLLOW_prefixDecl_in_prologue59 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
	public static final BitSet FOLLOW_BASE_in_baseDecl77 = new BitSet(new long[]{0x0000400000000000L});
	public static final BitSet FOLLOW_IRI_REF_in_baseDecl79 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PREFIX_in_prefixDecl96 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
	public static final BitSet FOLLOW_PNAME_NS_in_prefixDecl98 = new BitSet(new long[]{0x0000400000000000L});
	public static final BitSet FOLLOW_IRI_REF_in_prefixDecl100 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SELECT_in_selectQuery117 = new BitSet(new long[]{0x0000000008000200L,0x000000000C002000L});
	public static final BitSet FOLLOW_var_in_selectQuery132 = new BitSet(new long[]{0x8000008000000000L,0x000000002C000000L});
	public static final BitSet FOLLOW_ASTERISK_in_selectQuery137 = new BitSet(new long[]{0x8000008000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_datasetClause_in_selectQuery141 = new BitSet(new long[]{0x8000008000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_whereClause_in_selectQuery144 = new BitSet(new long[]{0x2100000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_solutionModifier_in_selectQuery146 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CONSTRUCT_in_constructQuery163 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_constructTemplate_in_constructQuery165 = new BitSet(new long[]{0x8000008000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_datasetClause_in_constructQuery167 = new BitSet(new long[]{0x8000008000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_whereClause_in_constructQuery170 = new BitSet(new long[]{0x2100000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_solutionModifier_in_constructQuery172 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DESCRIBE_in_describeQuery189 = new BitSet(new long[]{0x0000400000000200L,0x000000000C000060L});
	public static final BitSet FOLLOW_varOrIRIref_in_describeQuery193 = new BitSet(new long[]{0xA100408000000000L,0x000000002C000068L});
	public static final BitSet FOLLOW_ASTERISK_in_describeQuery198 = new BitSet(new long[]{0xA100008000000000L,0x0000000020000008L});
	public static final BitSet FOLLOW_datasetClause_in_describeQuery202 = new BitSet(new long[]{0xA100008000000000L,0x0000000020000008L});
	public static final BitSet FOLLOW_whereClause_in_describeQuery205 = new BitSet(new long[]{0x2100000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_solutionModifier_in_describeQuery208 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ASK_in_askQuery225 = new BitSet(new long[]{0x8000008000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_datasetClause_in_askQuery227 = new BitSet(new long[]{0x8000008000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_whereClause_in_askQuery230 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FROM_in_datasetClause247 = new BitSet(new long[]{0x0400400000000000L,0x0000000000000060L});
	public static final BitSet FOLLOW_defaultGraphClause_in_datasetClause251 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_namedGraphClause_in_datasetClause255 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_sourceSelector_in_defaultGraphClause274 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAMED_in_namedGraphClause291 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000060L});
	public static final BitSet FOLLOW_sourceSelector_in_namedGraphClause293 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_iriRef_in_sourceSelector310 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_WHERE_in_whereClause327 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_groupGraphPattern_in_whereClause330 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_orderClause_in_solutionModifier347 = new BitSet(new long[]{0x2100000000000002L});
	public static final BitSet FOLLOW_limitOffsetClauses_in_solutionModifier350 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_limitClause_in_limitOffsetClauses370 = new BitSet(new long[]{0x2000000000000002L});
	public static final BitSet FOLLOW_offsetClause_in_limitOffsetClauses372 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_offsetClause_in_limitOffsetClauses377 = new BitSet(new long[]{0x0100000000000002L});
	public static final BitSet FOLLOW_limitClause_in_limitOffsetClauses379 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ORDER_in_orderClause399 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_BY_in_orderClause401 = new BitSet(new long[]{0x401FC00001101080L,0x000000000C098060L});
	public static final BitSet FOLLOW_orderCondition_in_orderClause403 = new BitSet(new long[]{0x401FC00001101082L,0x000000000C098060L});
	public static final BitSet FOLLOW_set_in_orderCondition423 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_brackettedExpression_in_orderCondition433 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_constraint_in_orderCondition445 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_var_in_orderCondition449 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LIMIT_in_limitClause468 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_INTEGER_in_limitClause470 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OFFSET_in_offsetClause487 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_INTEGER_in_offsetClause489 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_CURLY_BRACE_in_groupGraphPattern506 = new BitSet(new long[]{0xC0007961C0E08800L,0x000000000DF00063L});
	public static final BitSet FOLLOW_triplesBlock_in_groupGraphPattern508 = new BitSet(new long[]{0x8000014000008000L,0x0000000000000002L});
	public static final BitSet FOLLOW_graphPatternNotTriples_in_groupGraphPattern515 = new BitSet(new long[]{0xC0007961E0E08800L,0x000000000DF00063L});
	public static final BitSet FOLLOW_filter_in_groupGraphPattern519 = new BitSet(new long[]{0xC0007961E0E08800L,0x000000000DF00063L});
	public static final BitSet FOLLOW_DOT_in_groupGraphPattern523 = new BitSet(new long[]{0xC0007961C0E08800L,0x000000000DF00063L});
	public static final BitSet FOLLOW_triplesBlock_in_groupGraphPattern526 = new BitSet(new long[]{0x8000014000008000L,0x0000000000000002L});
	public static final BitSet FOLLOW_CLOSE_CURLY_BRACE_in_groupGraphPattern532 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_triplesSameSubject_in_triplesBlock549 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_DOT_in_triplesBlock553 = new BitSet(new long[]{0x40007821C0E00802L,0x000000000DF00061L});
	public static final BitSet FOLLOW_triplesBlock_in_triplesBlock555 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_optionalGraphPattern_in_graphPatternNotTriples576 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_groupOrUnionGraphPattern_in_graphPatternNotTriples580 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_graphGraphPattern_in_graphPatternNotTriples584 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPTIONAL_in_optionalGraphPattern601 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_groupGraphPattern_in_optionalGraphPattern603 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GRAPH_in_graphGraphPattern620 = new BitSet(new long[]{0x0000400000000000L,0x000000000C000060L});
	public static final BitSet FOLLOW_varOrIRIref_in_graphGraphPattern622 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_groupGraphPattern_in_graphGraphPattern624 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_groupGraphPattern_in_groupOrUnionGraphPattern641 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
	public static final BitSet FOLLOW_UNION_in_groupOrUnionGraphPattern645 = new BitSet(new long[]{0x8000000000000000L});
	public static final BitSet FOLLOW_groupGraphPattern_in_groupOrUnionGraphPattern647 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
	public static final BitSet FOLLOW_FILTER_in_filter667 = new BitSet(new long[]{0x401FC00000101000L,0x0000000000098060L});
	public static final BitSet FOLLOW_constraint_in_filter669 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_brackettedExpression_in_constraint686 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_builtInCall_in_constraint690 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_functionCall_in_constraint694 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_iriRef_in_functionCall711 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_argList_in_functionCall713 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_argList732 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_argList734 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_argList738 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_argList740 = new BitSet(new long[]{0x0000000000024000L});
	public static final BitSet FOLLOW_COMMA_in_argList744 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_argList746 = new BitSet(new long[]{0x0000000000024000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_argList751 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_CURLY_BRACE_in_constructTemplate770 = new BitSet(new long[]{0x40007821C0E08800L,0x000000000DF00061L});
	public static final BitSet FOLLOW_constructTriples_in_constructTemplate772 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_CLOSE_CURLY_BRACE_in_constructTemplate775 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_triplesSameSubject_in_constructTriples792 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_DOT_in_constructTriples796 = new BitSet(new long[]{0x40007821C0E00802L,0x000000000DF00061L});
	public static final BitSet FOLLOW_constructTriples_in_constructTriples798 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_varOrTerm_in_triplesSameSubject819 = new BitSet(new long[]{0x0000400000000010L,0x000000000C000060L});
	public static final BitSet FOLLOW_propertyListNotEmpty_in_triplesSameSubject821 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_triplesNode_in_triplesSameSubject825 = new BitSet(new long[]{0x0000400000000010L,0x000000000C000060L});
	public static final BitSet FOLLOW_propertyList_in_triplesSameSubject827 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_verb_in_propertyListNotEmpty844 = new BitSet(new long[]{0x40007821C0E00800L,0x000000000DF00061L});
	public static final BitSet FOLLOW_objectList_in_propertyListNotEmpty846 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
	public static final BitSet FOLLOW_SEMICOLON_in_propertyListNotEmpty850 = new BitSet(new long[]{0x0000400000000012L,0x000000000C040060L});
	public static final BitSet FOLLOW_verb_in_propertyListNotEmpty854 = new BitSet(new long[]{0x40007821C0E00800L,0x000000000DF00061L});
	public static final BitSet FOLLOW_objectList_in_propertyListNotEmpty856 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
	public static final BitSet FOLLOW_propertyListNotEmpty_in_propertyList879 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_object_in_objectList897 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_COMMA_in_objectList901 = new BitSet(new long[]{0x40007821C0E00800L,0x000000000DF00061L});
	public static final BitSet FOLLOW_object_in_objectList903 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_graphNode_in_object923 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_varOrIRIref_in_verb940 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_A_in_verb948 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_collection_in_triplesNode965 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blankNodePropertyList_in_triplesNode973 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_SQUARE_BRACE_in_blankNodePropertyList990 = new BitSet(new long[]{0x0000400000000010L,0x000000000C000060L});
	public static final BitSet FOLLOW_propertyListNotEmpty_in_blankNodePropertyList992 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_CLOSE_SQUARE_BRACE_in_blankNodePropertyList994 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_collection1011 = new BitSet(new long[]{0x40007821C0E00800L,0x000000000DF00061L});
	public static final BitSet FOLLOW_graphNode_in_collection1013 = new BitSet(new long[]{0x40007821C0E04800L,0x000000000DF00061L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_collection1016 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_varOrTerm_in_graphNode1033 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_triplesNode_in_graphNode1037 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_var_in_varOrTerm1054 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_graphTerm_in_varOrTerm1062 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_var_in_varOrIRIref1079 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_iriRef_in_varOrIRIref1083 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_iriRef_in_graphTerm1125 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rdfLiteral_in_graphTerm1133 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericLiteral_in_graphTerm1141 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_booleanLiteral_in_graphTerm1149 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blankNode_in_graphTerm1157 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_graphTerm1165 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_graphTerm1167 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_conditionalOrExpression_in_expression1184 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression1201 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
	public static final BitSet FOLLOW_OR_in_conditionalOrExpression1205 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression1207 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
	public static final BitSet FOLLOW_valueLogical_in_conditionalAndExpression1227 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_AND_in_conditionalAndExpression1231 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_valueLogical_in_conditionalAndExpression1233 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_relationalExpression_in_valueLogical1253 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericExpression_in_relationalExpression1270 = new BitSet(new long[]{0x10C0060800000002L});
	public static final BitSet FOLLOW_EQUAL_in_relationalExpression1274 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_numericExpression_in_relationalExpression1276 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOT_EQUAL_in_relationalExpression1280 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_numericExpression_in_relationalExpression1282 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LESS_in_relationalExpression1286 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_numericExpression_in_relationalExpression1288 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GREATER_in_relationalExpression1292 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_numericExpression_in_relationalExpression1294 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LESS_EQUAL_in_relationalExpression1298 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_numericExpression_in_relationalExpression1300 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GREATER_EQUAL_in_relationalExpression1304 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_numericExpression_in_relationalExpression1306 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_additiveExpression_in_numericExpression1326 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1343 = new BitSet(new long[]{0x0200300180C00002L,0x0000000000000010L});
	public static final BitSet FOLLOW_PLUS_in_additiveExpression1347 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1349 = new BitSet(new long[]{0x0200300180C00002L,0x0000000000000010L});
	public static final BitSet FOLLOW_MINUS_in_additiveExpression1353 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1355 = new BitSet(new long[]{0x0200300180C00002L,0x0000000000000010L});
	public static final BitSet FOLLOW_numericLiteralPositive_in_additiveExpression1359 = new BitSet(new long[]{0x0200300180C00002L,0x0000000000000010L});
	public static final BitSet FOLLOW_numericLiteralNegative_in_additiveExpression1363 = new BitSet(new long[]{0x0200300180C00002L,0x0000000000000010L});
	public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1383 = new BitSet(new long[]{0x0000000010000202L});
	public static final BitSet FOLLOW_ASTERISK_in_multiplicativeExpression1387 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1389 = new BitSet(new long[]{0x0000000010000202L});
	public static final BitSet FOLLOW_DIVIDE_in_multiplicativeExpression1393 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1395 = new BitSet(new long[]{0x0000000010000202L});
	public static final BitSet FOLLOW_NOT_in_unaryExpression1415 = new BitSet(new long[]{0x401FF821C0F01000L,0x000000000DF98060L});
	public static final BitSet FOLLOW_primaryExpression_in_unaryExpression1417 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PLUS_in_unaryExpression1425 = new BitSet(new long[]{0x401FF821C0F01000L,0x000000000DF98060L});
	public static final BitSet FOLLOW_primaryExpression_in_unaryExpression1427 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_unaryExpression1435 = new BitSet(new long[]{0x401FF821C0F01000L,0x000000000DF98060L});
	public static final BitSet FOLLOW_primaryExpression_in_unaryExpression1437 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_primaryExpression_in_unaryExpression1445 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_brackettedExpression_in_primaryExpression1462 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_builtInCall_in_primaryExpression1466 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_iriRefOrFunction_in_primaryExpression1470 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rdfLiteral_in_primaryExpression1474 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericLiteral_in_primaryExpression1478 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_booleanLiteral_in_primaryExpression1482 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_var_in_primaryExpression1486 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_brackettedExpression1503 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_brackettedExpression1505 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_brackettedExpression1507 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STR_in_builtInCall1525 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_builtInCall1527 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1529 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_builtInCall1531 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LANG_in_builtInCall1539 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_builtInCall1541 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1543 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_builtInCall1545 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LANGMATCHES_in_builtInCall1553 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_builtInCall1555 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1557 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_COMMA_in_builtInCall1559 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1561 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_builtInCall1563 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DATATYPE_in_builtInCall1571 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_builtInCall1573 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1575 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_builtInCall1577 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BOUND_in_builtInCall1585 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_builtInCall1587 = new BitSet(new long[]{0x0000000000000000L,0x000000000C000000L});
	public static final BitSet FOLLOW_var_in_builtInCall1589 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_builtInCall1591 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SAMETERM_in_builtInCall1599 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_builtInCall1601 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1603 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_COMMA_in_builtInCall1605 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1607 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_builtInCall1609 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ISIRI_in_builtInCall1617 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_builtInCall1619 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1621 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_builtInCall1623 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ISURI_in_builtInCall1631 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_builtInCall1633 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1635 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_builtInCall1637 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ISBLANK_in_builtInCall1645 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_builtInCall1647 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1649 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_builtInCall1651 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ISLITERAL_in_builtInCall1659 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_builtInCall1661 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_builtInCall1663 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_builtInCall1665 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_regexExpression_in_builtInCall1673 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_REGEX_in_regexExpression1690 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_OPEN_BRACE_in_regexExpression1692 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_regexExpression1694 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_COMMA_in_regexExpression1696 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_regexExpression1698 = new BitSet(new long[]{0x0000000000024000L});
	public static final BitSet FOLLOW_COMMA_in_regexExpression1702 = new BitSet(new long[]{0x4A1FF821C0F01000L,0x000000000DF98070L});
	public static final BitSet FOLLOW_expression_in_regexExpression1704 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_CLOSE_BRACE_in_regexExpression1709 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_iriRef_in_iriRefOrFunction1726 = new BitSet(new long[]{0x4000000000000002L});
	public static final BitSet FOLLOW_argList_in_iriRefOrFunction1728 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_string_in_rdfLiteral1746 = new BitSet(new long[]{0x0020000000000002L,0x0000000000004000L});
	public static final BitSet FOLLOW_LANGTAG_in_rdfLiteral1750 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_REFERENCE_in_rdfLiteral1756 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000060L});
	public static final BitSet FOLLOW_iriRef_in_rdfLiteral1758 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericLiteralUnsigned_in_numericLiteral1780 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericLiteralPositive_in_numericLiteral1784 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericLiteralNegative_in_numericLiteral1788 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IRI_REF_in_iriRef1970 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_prefixedName_in_iriRef1978 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BLANK_NODE_LABEL_in_blankNode2020 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_OPEN_SQUARE_BRACE_in_blankNode2028 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_CLOSE_SQUARE_BRACE_in_blankNode2030 = new BitSet(new long[]{0x0000000000000002L});
}
