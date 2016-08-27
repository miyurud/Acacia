package org.acacia.rdf.sparql.java;

// $ANTLR 3.5.2 /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g 2015-07-03 14:44:15

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class SparqlLexer extends Lexer {
    public static final int EOF = -1;
    public static final int A = 4;
    public static final int AND = 5;
    public static final int ANY = 6;
    public static final int ASC = 7;
    public static final int ASK = 8;
    public static final int ASTERISK = 9;
    public static final int BASE = 10;
    public static final int BLANK_NODE_LABEL = 11;
    public static final int BOUND = 12;
    public static final int BY = 13;
    public static final int CLOSE_BRACE = 14;
    public static final int CLOSE_CURLY_BRACE = 15;
    public static final int CLOSE_SQUARE_BRACE = 16;
    public static final int COMMA = 17;
    public static final int COMMENT = 18;
    public static final int CONSTRUCT = 19;
    public static final int DATATYPE = 20;
    public static final int DECIMAL = 21;
    public static final int DECIMAL_NEGATIVE = 22;
    public static final int DECIMAL_POSITIVE = 23;
    public static final int DESC = 24;
    public static final int DESCRIBE = 25;
    public static final int DIGIT = 26;
    public static final int DISTINCT = 27;
    public static final int DIVIDE = 28;
    public static final int DOT = 29;
    public static final int DOUBLE = 30;
    public static final int DOUBLE_NEGATIVE = 31;
    public static final int DOUBLE_POSITIVE = 32;
    public static final int ECHAR = 33;
    public static final int EOL = 34;
    public static final int EQUAL = 35;
    public static final int EXPONENT = 36;
    public static final int FALSE = 37;
    public static final int FILTER = 38;
    public static final int FROM = 39;
    public static final int GRAPH = 40;
    public static final int GREATER = 41;
    public static final int GREATER_EQUAL = 42;
    public static final int INTEGER = 43;
    public static final int INTEGER_NEGATIVE = 44;
    public static final int INTEGER_POSITIVE = 45;
    public static final int IRI_REF = 46;
    public static final int ISBLANK = 47;
    public static final int ISIRI = 48;
    public static final int ISLITERAL = 49;
    public static final int ISURI = 50;
    public static final int LANG = 51;
    public static final int LANGMATCHES = 52;
    public static final int LANGTAG = 53;
    public static final int LESS = 54;
    public static final int LESS_EQUAL = 55;
    public static final int LIMIT = 56;
    public static final int MINUS = 57;
    public static final int NAMED = 58;
    public static final int NOT = 59;
    public static final int NOT_EQUAL = 60;
    public static final int OFFSET = 61;
    public static final int OPEN_BRACE = 62;
    public static final int OPEN_CURLY_BRACE = 63;
    public static final int OPEN_SQUARE_BRACE = 64;
    public static final int OPTIONAL = 65;
    public static final int OR = 66;
    public static final int ORDER = 67;
    public static final int PLUS = 68;
    public static final int PNAME_LN = 69;
    public static final int PNAME_NS = 70;
    public static final int PN_CHARS = 71;
    public static final int PN_CHARS_BASE = 72;
    public static final int PN_CHARS_U = 73;
    public static final int PN_LOCAL = 74;
    public static final int PN_PREFIX = 75;
    public static final int PREFIX = 76;
    public static final int REDUCED = 77;
    public static final int REFERENCE = 78;
    public static final int REGEX = 79;
    public static final int SAMETERM = 80;
    public static final int SELECT = 81;
    public static final int SEMICOLON = 82;
    public static final int STR = 83;
    public static final int STRING_LITERAL1 = 84;
    public static final int STRING_LITERAL2 = 85;
    public static final int STRING_LITERAL_LONG1 = 86;
    public static final int STRING_LITERAL_LONG2 = 87;
    public static final int TRUE = 88;
    public static final int UNION = 89;
    public static final int VAR1 = 90;
    public static final int VAR2 = 91;
    public static final int VARNAME = 92;
    public static final int WHERE = 93;
    public static final int WS = 94;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public SparqlLexer() {
    }

    public SparqlLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }

    public SparqlLexer(CharStream input, RecognizerSharedState state) {
        super(input, state);
    }

    @Override
    public String getGrammarFileName() {
        return "/home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g";
    }

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:379:5:
            // ( ( ' ' | '\\t' | EOL )+ )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:379:7:
            // ( ' ' | '\\t' | EOL )+
            {
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:379:7:
                // ( ' ' | '\\t' | EOL )+
                int cnt1 = 0;
                loop1: while (true) {
                    int alt1 = 2;
                    int LA1_0 = input.LA(1);
                    if (((LA1_0 >= '\t' && LA1_0 <= '\n') || LA1_0 == '\r' || LA1_0 == ' ')) {
                        alt1 = 1;
                    }

                    switch (alt1) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '\t' && input.LA(1) <= '\n')
                                || input.LA(1) == '\r' || input.LA(1) == ' ') {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        if (cnt1 >= 1)
                            break loop1;
                        EarlyExitException eee = new EarlyExitException(1,
                                input);
                        throw eee;
                    }
                    cnt1++;
                }

                _channel = HIDDEN;
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "WS"

    // $ANTLR start "PNAME_NS"
    public final void mPNAME_NS() throws RecognitionException {
        try {
            int _type = PNAME_NS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken p = null;

            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:383:5:
            // ( (p= PN_PREFIX )? ':' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:383:7:
            // (p= PN_PREFIX )? ':'
            {
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:383:8:
                // (p= PN_PREFIX )?
                int alt2 = 2;
                int LA2_0 = input.LA(1);
                if (((LA2_0 >= 'A' && LA2_0 <= 'Z')
                        || (LA2_0 >= 'a' && LA2_0 <= 'z')
                        || (LA2_0 >= '\u00C0' && LA2_0 <= '\u00D6')
                        || (LA2_0 >= '\u00D8' && LA2_0 <= '\u00F6')
                        || (LA2_0 >= '\u00F8' && LA2_0 <= '\u02FF')
                        || (LA2_0 >= '\u0370' && LA2_0 <= '\u037D')
                        || (LA2_0 >= '\u037F' && LA2_0 <= '\u1FFF')
                        || (LA2_0 >= '\u200C' && LA2_0 <= '\u200D')
                        || (LA2_0 >= '\u2070' && LA2_0 <= '\u218F')
                        || (LA2_0 >= '\u2C00' && LA2_0 <= '\u2FEF')
                        || (LA2_0 >= '\u3001' && LA2_0 <= '\uD7FF')
                        || (LA2_0 >= '\uF900' && LA2_0 <= '\uFDCF') || (LA2_0 >= '\uFDF0' && LA2_0 <= '\uFFFD'))) {
                    alt2 = 1;
                }
                switch (alt2) {
                case 1:
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:383:8:
                // p= PN_PREFIX
                {
                    int pStart45 = getCharIndex();
                    int pStartLine45 = getLine();
                    int pStartCharPos45 = getCharPositionInLine();
                    mPN_PREFIX();
                    p = new CommonToken(input, Token.INVALID_TOKEN_TYPE,
                            Token.DEFAULT_CHANNEL, pStart45, getCharIndex() - 1);
                    p.setLine(pStartLine45);
                    p.setCharPositionInLine(pStartCharPos45);

                }
                    break;

                }

                match(':');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "PNAME_NS"

    // $ANTLR start "PNAME_LN"
    public final void mPNAME_LN() throws RecognitionException {
        try {
            int _type = PNAME_LN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:387:5:
            // ( PNAME_NS PN_LOCAL )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:387:7:
            // PNAME_NS PN_LOCAL
            {
                mPNAME_NS();

                mPN_LOCAL();

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "PNAME_LN"

    // $ANTLR start "BASE"
    public final void mBASE() throws RecognitionException {
        try {
            int _type = BASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:391:5:
            // ( ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:391:7:
            // ( 'B' | 'b' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'E' | 'e' )
            {
                if (input.LA(1) == 'B' || input.LA(1) == 'b') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "BASE"

    // $ANTLR start "PREFIX"
    public final void mPREFIX() throws RecognitionException {
        try {
            int _type = PREFIX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:395:5:
            // ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) ( 'I' |
            // 'i' ) ( 'X' | 'x' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:395:7:
            // ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'F' | 'f' ) ( 'I' |
            // 'i' ) ( 'X' | 'x' )
            {
                if (input.LA(1) == 'P' || input.LA(1) == 'p') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'F' || input.LA(1) == 'f') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'X' || input.LA(1) == 'x') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "PREFIX"

    // $ANTLR start "SELECT"
    public final void mSELECT() throws RecognitionException {
        try {
            int _type = SELECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:399:5:
            // ( ( 'S' | 's' ) ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'C' |
            // 'c' ) ( 'T' | 't' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:399:7:
            // ( 'S' | 's' ) ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'C' |
            // 'c' ) ( 'T' | 't' )
            {
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "SELECT"

    // $ANTLR start "DISTINCT"
    public final void mDISTINCT() throws RecognitionException {
        try {
            int _type = DISTINCT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:403:5:
            // ( ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'I' |
            // 'i' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:403:7:
            // ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'I' |
            // 'i' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' )
            {
                if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DISTINCT"

    // $ANTLR start "REDUCED"
    public final void mREDUCED() throws RecognitionException {
        try {
            int _type = REDUCED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:407:5:
            // ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'D' | 'd' ) ( 'U' | 'u' ) ( 'C' |
            // 'c' ) ( 'E' | 'e' ) ( 'D' | 'd' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:407:7:
            // ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'D' | 'd' ) ( 'U' | 'u' ) ( 'C' |
            // 'c' ) ( 'E' | 'e' ) ( 'D' | 'd' )
            {
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'U' || input.LA(1) == 'u') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "REDUCED"

    // $ANTLR start "CONSTRUCT"
    public final void mCONSTRUCT() throws RecognitionException {
        try {
            int _type = CONSTRUCT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:411:5:
            // ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' |
            // 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:411:7:
            // ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'S' | 's' ) ( 'T' |
            // 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'C' | 'c' ) ( 'T' | 't' )
            {
                if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'O' || input.LA(1) == 'o') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'U' || input.LA(1) == 'u') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "CONSTRUCT"

    // $ANTLR start "DESCRIBE"
    public final void mDESCRIBE() throws RecognitionException {
        try {
            int _type = DESCRIBE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:415:5:
            // ( ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'C' | 'c' ) ( 'R' |
            // 'r' ) ( 'I' | 'i' ) ( 'B' | 'b' ) ( 'E' | 'e' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:415:7:
            // ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'C' | 'c' ) ( 'R' |
            // 'r' ) ( 'I' | 'i' ) ( 'B' | 'b' ) ( 'E' | 'e' )
            {
                if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'B' || input.LA(1) == 'b') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DESCRIBE"

    // $ANTLR start "ASK"
    public final void mASK() throws RecognitionException {
        try {
            int _type = ASK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:419:5:
            // ( ( 'A' | 'a' ) ( 'S' | 's' ) ( 'K' | 'k' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:419:7:
            // ( 'A' | 'a' ) ( 'S' | 's' ) ( 'K' | 'k' )
            {
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'K' || input.LA(1) == 'k') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "ASK"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:423:5:
            // ( ( 'F' | 'f' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'M' | 'm' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:423:7:
            // ( 'F' | 'f' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'M' | 'm' )
            {
                if (input.LA(1) == 'F' || input.LA(1) == 'f') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'O' || input.LA(1) == 'o') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'M' || input.LA(1) == 'm') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "FROM"

    // $ANTLR start "NAMED"
    public final void mNAMED() throws RecognitionException {
        try {
            int _type = NAMED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:427:5:
            // ( ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'D' |
            // 'd' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:427:7:
            // ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'D' |
            // 'd' )
            {
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'M' || input.LA(1) == 'm') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "NAMED"

    // $ANTLR start "WHERE"
    public final void mWHERE() throws RecognitionException {
        try {
            int _type = WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:431:5:
            // ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'E' |
            // 'e' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:431:7:
            // ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'E' |
            // 'e' )
            {
                if (input.LA(1) == 'W' || input.LA(1) == 'w') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'H' || input.LA(1) == 'h') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "WHERE"

    // $ANTLR start "ORDER"
    public final void mORDER() throws RecognitionException {
        try {
            int _type = ORDER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:435:5:
            // ( ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'R' |
            // 'r' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:435:7:
            // ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'R' |
            // 'r' )
            {
                if (input.LA(1) == 'O' || input.LA(1) == 'o') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "ORDER"

    // $ANTLR start "BY"
    public final void mBY() throws RecognitionException {
        try {
            int _type = BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:439:5:
            // ( ( 'B' | 'b' ) ( 'Y' | 'y' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:439:7:
            // ( 'B' | 'b' ) ( 'Y' | 'y' )
            {
                if (input.LA(1) == 'B' || input.LA(1) == 'b') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'Y' || input.LA(1) == 'y') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "BY"

    // $ANTLR start "ASC"
    public final void mASC() throws RecognitionException {
        try {
            int _type = ASC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:443:5:
            // ( ( 'A' | 'a' ) ( 'S' | 's' ) ( 'C' | 'c' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:443:7:
            // ( 'A' | 'a' ) ( 'S' | 's' ) ( 'C' | 'c' )
            {
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "ASC"

    // $ANTLR start "DESC"
    public final void mDESC() throws RecognitionException {
        try {
            int _type = DESC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:447:5:
            // ( ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'C' | 'c' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:447:7:
            // ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'C' | 'c' )
            {
                if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DESC"

    // $ANTLR start "LIMIT"
    public final void mLIMIT() throws RecognitionException {
        try {
            int _type = LIMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:451:5:
            // ( ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'T' |
            // 't' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:451:7:
            // ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'T' |
            // 't' )
            {
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'M' || input.LA(1) == 'm') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "LIMIT"

    // $ANTLR start "OFFSET"
    public final void mOFFSET() throws RecognitionException {
        try {
            int _type = OFFSET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:455:5:
            // ( ( 'O' | 'o' ) ( 'F' | 'f' ) ( 'F' | 'f' ) ( 'S' | 's' ) ( 'E' |
            // 'e' ) ( 'T' | 't' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:455:7:
            // ( 'O' | 'o' ) ( 'F' | 'f' ) ( 'F' | 'f' ) ( 'S' | 's' ) ( 'E' |
            // 'e' ) ( 'T' | 't' )
            {
                if (input.LA(1) == 'O' || input.LA(1) == 'o') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'F' || input.LA(1) == 'f') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'F' || input.LA(1) == 'f') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "OFFSET"

    // $ANTLR start "OPTIONAL"
    public final void mOPTIONAL() throws RecognitionException {
        try {
            int _type = OPTIONAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:459:5:
            // ( ( 'O' | 'o' ) ( 'P' | 'p' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' |
            // 'o' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:459:7:
            // ( 'O' | 'o' ) ( 'P' | 'p' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' |
            // 'o' ) ( 'N' | 'n' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
                if (input.LA(1) == 'O' || input.LA(1) == 'o') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'P' || input.LA(1) == 'p') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'O' || input.LA(1) == 'o') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "OPTIONAL"

    // $ANTLR start "GRAPH"
    public final void mGRAPH() throws RecognitionException {
        try {
            int _type = GRAPH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:463:5:
            // ( ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'P' | 'p' ) ( 'H' |
            // 'h' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:463:7:
            // ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'P' | 'p' ) ( 'H' |
            // 'h' )
            {
                if (input.LA(1) == 'G' || input.LA(1) == 'g') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'P' || input.LA(1) == 'p') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'H' || input.LA(1) == 'h') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "GRAPH"

    // $ANTLR start "UNION"
    public final void mUNION() throws RecognitionException {
        try {
            int _type = UNION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:467:5:
            // ( ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' |
            // 'n' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:467:7:
            // ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' |
            // 'n' )
            {
                if (input.LA(1) == 'U' || input.LA(1) == 'u') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'O' || input.LA(1) == 'o') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "UNION"

    // $ANTLR start "FILTER"
    public final void mFILTER() throws RecognitionException {
        try {
            int _type = FILTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:471:5:
            // ( ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'T' | 't' ) ( 'E' |
            // 'e' ) ( 'R' | 'r' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:471:7:
            // ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'T' | 't' ) ( 'E' |
            // 'e' ) ( 'R' | 'r' )
            {
                if (input.LA(1) == 'F' || input.LA(1) == 'f') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "FILTER"

    // $ANTLR start "A"
    public final void mA() throws RecognitionException {
        try {
            int _type = A;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:475:5:
            // ( 'a' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:475:7:
            // 'a'
            {
                match('a');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "A"

    // $ANTLR start "STR"
    public final void mSTR() throws RecognitionException {
        try {
            int _type = STR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:479:5:
            // ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:479:7:
            // ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' )
            {
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "STR"

    // $ANTLR start "LANG"
    public final void mLANG() throws RecognitionException {
        try {
            int _type = LANG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:483:5:
            // ( ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:483:7:
            // ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'G' | 'g' )
            {
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'G' || input.LA(1) == 'g') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "LANG"

    // $ANTLR start "LANGMATCHES"
    public final void mLANGMATCHES() throws RecognitionException {
        try {
            int _type = LANGMATCHES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:487:5:
            // ( ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'G' | 'g' ) ( 'M' |
            // 'm' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'C' | 'c' ) ( 'H' | 'h' ) (
            // 'E' | 'e' ) ( 'S' | 's' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:487:7:
            // ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'G' | 'g' ) ( 'M' |
            // 'm' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'C' | 'c' ) ( 'H' | 'h' ) (
            // 'E' | 'e' ) ( 'S' | 's' )
            {
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'G' || input.LA(1) == 'g') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'M' || input.LA(1) == 'm') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'C' || input.LA(1) == 'c') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'H' || input.LA(1) == 'h') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "LANGMATCHES"

    // $ANTLR start "DATATYPE"
    public final void mDATATYPE() throws RecognitionException {
        try {
            int _type = DATATYPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:491:5:
            // ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'T' |
            // 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:491:7:
            // ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'T' |
            // 't' ) ( 'Y' | 'y' ) ( 'P' | 'p' ) ( 'E' | 'e' )
            {
                if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'Y' || input.LA(1) == 'y') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'P' || input.LA(1) == 'p') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DATATYPE"

    // $ANTLR start "BOUND"
    public final void mBOUND() throws RecognitionException {
        try {
            int _type = BOUND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:495:5:
            // ( ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'D' |
            // 'd' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:495:7:
            // ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'D' |
            // 'd' )
            {
                if (input.LA(1) == 'B' || input.LA(1) == 'b') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'O' || input.LA(1) == 'o') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'U' || input.LA(1) == 'u') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'D' || input.LA(1) == 'd') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "BOUND"

    // $ANTLR start "SAMETERM"
    public final void mSAMETERM() throws RecognitionException {
        try {
            int _type = SAMETERM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:499:5:
            // ( ( 'S' | 's' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' |
            // 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'M' | 'm' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:499:7:
            // ( 'S' | 's' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'T' |
            // 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'M' | 'm' )
            {
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'M' || input.LA(1) == 'm') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'M' || input.LA(1) == 'm') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "SAMETERM"

    // $ANTLR start "ISIRI"
    public final void mISIRI() throws RecognitionException {
        try {
            int _type = ISIRI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:503:5:
            // ( ( 'I' | 'i' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'R' | 'r' ) ( 'I' |
            // 'i' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:503:7:
            // ( 'I' | 'i' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'R' | 'r' ) ( 'I' |
            // 'i' )
            {
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "ISIRI"

    // $ANTLR start "ISURI"
    public final void mISURI() throws RecognitionException {
        try {
            int _type = ISURI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:507:5:
            // ( ( 'I' | 'i' ) ( 'S' | 's' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'I' |
            // 'i' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:507:7:
            // ( 'I' | 'i' ) ( 'S' | 's' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'I' |
            // 'i' )
            {
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'U' || input.LA(1) == 'u') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "ISURI"

    // $ANTLR start "ISBLANK"
    public final void mISBLANK() throws RecognitionException {
        try {
            int _type = ISBLANK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:511:5:
            // ( ( 'I' | 'i' ) ( 'S' | 's' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'A' |
            // 'a' ) ( 'N' | 'n' ) ( 'K' | 'k' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:511:7:
            // ( 'I' | 'i' ) ( 'S' | 's' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'A' |
            // 'a' ) ( 'N' | 'n' ) ( 'K' | 'k' )
            {
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'B' || input.LA(1) == 'b') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'N' || input.LA(1) == 'n') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'K' || input.LA(1) == 'k') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "ISBLANK"

    // $ANTLR start "ISLITERAL"
    public final void mISLITERAL() throws RecognitionException {
        try {
            int _type = ISLITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:515:5:
            // ( ( 'I' | 'i' ) ( 'S' | 's' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'T' |
            // 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:515:7:
            // ( 'I' | 'i' ) ( 'S' | 's' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'T' |
            // 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'I' || input.LA(1) == 'i') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "ISLITERAL"

    // $ANTLR start "REGEX"
    public final void mREGEX() throws RecognitionException {
        try {
            int _type = REGEX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:519:5:
            // ( ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'G' | 'g' ) ( 'E' | 'e' ) ( 'X' |
            // 'x' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:519:7:
            // ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'G' | 'g' ) ( 'E' | 'e' ) ( 'X' |
            // 'x' )
            {
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'G' || input.LA(1) == 'g') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'X' || input.LA(1) == 'x') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "REGEX"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:523:5:
            // ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:523:7:
            // ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
            {
                if (input.LA(1) == 'T' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'R' || input.LA(1) == 'r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'U' || input.LA(1) == 'u') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "TRUE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:527:5:
            // ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' |
            // 'e' ) )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:527:7:
            // ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' |
            // 'e' )
            {
                if (input.LA(1) == 'F' || input.LA(1) == 'f') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'A' || input.LA(1) == 'a') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'L' || input.LA(1) == 'l') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'S' || input.LA(1) == 's') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "FALSE"

    // $ANTLR start "IRI_REF"
    public final void mIRI_REF() throws RecognitionException {
        try {
            int _type = IRI_REF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:531:5:
            // ( LESS ( options {greedy=false; } :~ ( LESS | GREATER | '\"' |
            // OPEN_CURLY_BRACE | CLOSE_CURLY_BRACE | '|' | '^' | '\\\\' | '`' |
            // ( '\\u0000' .. '\ ' ) ) )* GREATER )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:531:7:
            // LESS ( options {greedy=false; } :~ ( LESS | GREATER | '\"' |
            // OPEN_CURLY_BRACE | CLOSE_CURLY_BRACE | '|' | '^' | '\\\\' | '`' |
            // ( '\\u0000' .. '\ ' ) ) )* GREATER
            {
                mLESS();

                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:531:12:
                // ( options {greedy=false; } :~ ( LESS | GREATER | '\"' |
                // OPEN_CURLY_BRACE | CLOSE_CURLY_BRACE | '|' | '^' | '\\\\' |
                // '`' | ( '\\u0000' .. '\ ' ) ) )*
                loop3: while (true) {
                    int alt3 = 2;
                    int LA3_0 = input.LA(1);
                    if ((LA3_0 == '!' || (LA3_0 >= '#' && LA3_0 <= ';')
                            || LA3_0 == '=' || (LA3_0 >= '?' && LA3_0 <= '[')
                            || LA3_0 == ']' || LA3_0 == '_'
                            || (LA3_0 >= 'a' && LA3_0 <= 'z') || (LA3_0 >= '~' && LA3_0 <= '\uFFFF'))) {
                        alt3 = 1;
                    } else if ((LA3_0 == '>')) {
                        alt3 = 2;
                    }

                    switch (alt3) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:531:40:
                    // ~ ( LESS | GREATER | '\"' | OPEN_CURLY_BRACE |
                    // CLOSE_CURLY_BRACE | '|' | '^' | '\\\\' | '`' | (
                    // '\\u0000' .. '\ ' ) )
                    {
                        if (input.LA(1) == '!'
                                || (input.LA(1) >= '#' && input.LA(1) <= ';')
                                || input.LA(1) == '='
                                || (input.LA(1) >= '?' && input.LA(1) <= '[')
                                || input.LA(1) == ']'
                                || input.LA(1) == '_'
                                || (input.LA(1) >= 'a' && input.LA(1) <= 'z')
                                || (input.LA(1) >= '~' && input.LA(1) <= '\uFFFF')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        break loop3;
                    }
                }

                mGREATER();

                setText(getText().substring(1, getText().length() - 1));
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "IRI_REF"

    // $ANTLR start "BLANK_NODE_LABEL"
    public final void mBLANK_NODE_LABEL() throws RecognitionException {
        try {
            int _type = BLANK_NODE_LABEL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken t = null;

            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:535:5:
            // ( '_:' t= PN_LOCAL )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:535:7:
            // '_:' t= PN_LOCAL
            {
                match("_:");

                int tStart1656 = getCharIndex();
                int tStartLine1656 = getLine();
                int tStartCharPos1656 = getCharPositionInLine();
                mPN_LOCAL();
                t = new CommonToken(input, Token.INVALID_TOKEN_TYPE,
                        Token.DEFAULT_CHANNEL, tStart1656, getCharIndex() - 1);
                t.setLine(tStartLine1656);
                t.setCharPositionInLine(tStartCharPos1656);

                setText((t != null ? t.getText() : null));
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "BLANK_NODE_LABEL"

    // $ANTLR start "VAR1"
    public final void mVAR1() throws RecognitionException {
        try {
            int _type = VAR1;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken v = null;

            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:539:5:
            // ( '?' v= VARNAME )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:539:7:
            // '?' v= VARNAME
            {
                match('?');
                int vStart1678 = getCharIndex();
                int vStartLine1678 = getLine();
                int vStartCharPos1678 = getCharPositionInLine();
                mVARNAME();
                v = new CommonToken(input, Token.INVALID_TOKEN_TYPE,
                        Token.DEFAULT_CHANNEL, vStart1678, getCharIndex() - 1);
                v.setLine(vStartLine1678);
                v.setCharPositionInLine(vStartCharPos1678);

                setText((v != null ? v.getText() : null));
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "VAR1"

    // $ANTLR start "VAR2"
    public final void mVAR2() throws RecognitionException {
        try {
            int _type = VAR2;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken v = null;

            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:543:5:
            // ( '$' v= VARNAME )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:543:7:
            // '$' v= VARNAME
            {
                match('$');
                int vStart1700 = getCharIndex();
                int vStartLine1700 = getLine();
                int vStartCharPos1700 = getCharPositionInLine();
                mVARNAME();
                v = new CommonToken(input, Token.INVALID_TOKEN_TYPE,
                        Token.DEFAULT_CHANNEL, vStart1700, getCharIndex() - 1);
                v.setLine(vStartLine1700);
                v.setCharPositionInLine(vStartCharPos1700);

                setText((v != null ? v.getText() : null));
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "VAR2"

    // $ANTLR start "LANGTAG"
    public final void mLANGTAG() throws RecognitionException {
        try {
            int _type = LANGTAG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:547:5:
            // ( '@' ( PN_CHARS_BASE )+ ( MINUS ( PN_CHARS_BASE DIGIT )+ )* )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:547:7:
            // '@' ( PN_CHARS_BASE )+ ( MINUS ( PN_CHARS_BASE DIGIT )+ )*
            {
                match('@');
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:547:11:
                // ( PN_CHARS_BASE )+
                int cnt4 = 0;
                loop4: while (true) {
                    int alt4 = 2;
                    int LA4_0 = input.LA(1);
                    if (((LA4_0 >= 'A' && LA4_0 <= 'Z')
                            || (LA4_0 >= 'a' && LA4_0 <= 'z')
                            || (LA4_0 >= '\u00C0' && LA4_0 <= '\u00D6')
                            || (LA4_0 >= '\u00D8' && LA4_0 <= '\u00F6')
                            || (LA4_0 >= '\u00F8' && LA4_0 <= '\u02FF')
                            || (LA4_0 >= '\u0370' && LA4_0 <= '\u037D')
                            || (LA4_0 >= '\u037F' && LA4_0 <= '\u1FFF')
                            || (LA4_0 >= '\u200C' && LA4_0 <= '\u200D')
                            || (LA4_0 >= '\u2070' && LA4_0 <= '\u218F')
                            || (LA4_0 >= '\u2C00' && LA4_0 <= '\u2FEF')
                            || (LA4_0 >= '\u3001' && LA4_0 <= '\uD7FF')
                            || (LA4_0 >= '\uF900' && LA4_0 <= '\uFDCF') || (LA4_0 >= '\uFDF0' && LA4_0 <= '\uFFFD'))) {
                        alt4 = 1;
                    }

                    switch (alt4) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= 'A' && input.LA(1) <= 'Z')
                                || (input.LA(1) >= 'a' && input.LA(1) <= 'z')
                                || (input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')
                                || (input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')
                                || (input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')
                                || (input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')
                                || (input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')
                                || (input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')
                                || (input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')
                                || (input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')
                                || (input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')
                                || (input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')
                                || (input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        if (cnt4 >= 1)
                            break loop4;
                        EarlyExitException eee = new EarlyExitException(4,
                                input);
                        throw eee;
                    }
                    cnt4++;
                }

                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:547:26:
                // ( MINUS ( PN_CHARS_BASE DIGIT )+ )*
                loop6: while (true) {
                    int alt6 = 2;
                    int LA6_0 = input.LA(1);
                    if ((LA6_0 == '-')) {
                        alt6 = 1;
                    }

                    switch (alt6) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:547:27:
                    // MINUS ( PN_CHARS_BASE DIGIT )+
                    {
                        mMINUS();

                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:547:33:
                        // ( PN_CHARS_BASE DIGIT )+
                        int cnt5 = 0;
                        loop5: while (true) {
                            int alt5 = 2;
                            int LA5_0 = input.LA(1);
                            if (((LA5_0 >= 'A' && LA5_0 <= 'Z')
                                    || (LA5_0 >= 'a' && LA5_0 <= 'z')
                                    || (LA5_0 >= '\u00C0' && LA5_0 <= '\u00D6')
                                    || (LA5_0 >= '\u00D8' && LA5_0 <= '\u00F6')
                                    || (LA5_0 >= '\u00F8' && LA5_0 <= '\u02FF')
                                    || (LA5_0 >= '\u0370' && LA5_0 <= '\u037D')
                                    || (LA5_0 >= '\u037F' && LA5_0 <= '\u1FFF')
                                    || (LA5_0 >= '\u200C' && LA5_0 <= '\u200D')
                                    || (LA5_0 >= '\u2070' && LA5_0 <= '\u218F')
                                    || (LA5_0 >= '\u2C00' && LA5_0 <= '\u2FEF')
                                    || (LA5_0 >= '\u3001' && LA5_0 <= '\uD7FF')
                                    || (LA5_0 >= '\uF900' && LA5_0 <= '\uFDCF') || (LA5_0 >= '\uFDF0' && LA5_0 <= '\uFFFD'))) {
                                alt5 = 1;
                            }

                            switch (alt5) {
                            case 1:
                            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:547:34:
                            // PN_CHARS_BASE DIGIT
                            {
                                mPN_CHARS_BASE();

                                mDIGIT();

                            }
                                break;

                            default:
                                if (cnt5 >= 1)
                                    break loop5;
                                EarlyExitException eee = new EarlyExitException(
                                        5, input);
                                throw eee;
                            }
                            cnt5++;
                        }

                    }
                        break;

                    default:
                        break loop6;
                    }
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "LANGTAG"

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:551:5:
            // ( ( DIGIT )+ )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:551:7:
            // ( DIGIT )+
            {
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:551:7:
                // ( DIGIT )+
                int cnt7 = 0;
                loop7: while (true) {
                    int alt7 = 2;
                    int LA7_0 = input.LA(1);
                    if (((LA7_0 >= '0' && LA7_0 <= '9'))) {
                        alt7 = 1;
                    }

                    switch (alt7) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        if (cnt7 >= 1)
                            break loop7;
                        EarlyExitException eee = new EarlyExitException(7,
                                input);
                        throw eee;
                    }
                    cnt7++;
                }

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "INTEGER"

    // $ANTLR start "DECIMAL"
    public final void mDECIMAL() throws RecognitionException {
        try {
            int _type = DECIMAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:555:5:
            // ( ( DIGIT )+ DOT ( DIGIT )* | DOT ( DIGIT )+ )
            int alt11 = 2;
            int LA11_0 = input.LA(1);
            if (((LA11_0 >= '0' && LA11_0 <= '9'))) {
                alt11 = 1;
            } else if ((LA11_0 == '.')) {
                alt11 = 2;
            }

            else {
                NoViableAltException nvae = new NoViableAltException("", 11, 0,
                        input);
                throw nvae;
            }

            switch (alt11) {
            case 1:
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:555:7:
            // ( DIGIT )+ DOT ( DIGIT )*
            {
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:555:7:
                // ( DIGIT )+
                int cnt8 = 0;
                loop8: while (true) {
                    int alt8 = 2;
                    int LA8_0 = input.LA(1);
                    if (((LA8_0 >= '0' && LA8_0 <= '9'))) {
                        alt8 = 1;
                    }

                    switch (alt8) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        if (cnt8 >= 1)
                            break loop8;
                        EarlyExitException eee = new EarlyExitException(8,
                                input);
                        throw eee;
                    }
                    cnt8++;
                }

                mDOT();

                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:555:18:
                // ( DIGIT )*
                loop9: while (true) {
                    int alt9 = 2;
                    int LA9_0 = input.LA(1);
                    if (((LA9_0 >= '0' && LA9_0 <= '9'))) {
                        alt9 = 1;
                    }

                    switch (alt9) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        break loop9;
                    }
                }

            }
                break;
            case 2:
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:556:7:
            // DOT ( DIGIT )+
            {
                mDOT();

                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:556:11:
                // ( DIGIT )+
                int cnt10 = 0;
                loop10: while (true) {
                    int alt10 = 2;
                    int LA10_0 = input.LA(1);
                    if (((LA10_0 >= '0' && LA10_0 <= '9'))) {
                        alt10 = 1;
                    }

                    switch (alt10) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        if (cnt10 >= 1)
                            break loop10;
                        EarlyExitException eee = new EarlyExitException(10,
                                input);
                        throw eee;
                    }
                    cnt10++;
                }

            }
                break;

            }
            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DECIMAL"

    // $ANTLR start "DOUBLE"
    public final void mDOUBLE() throws RecognitionException {
        try {
            int _type = DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:560:5:
            // ( ( DIGIT )+ DOT ( DIGIT )* EXPONENT | DOT ( DIGIT )+ EXPONENT |
            // ( DIGIT )+ EXPONENT )
            int alt16 = 3;
            alt16 = dfa16.predict(input);
            switch (alt16) {
            case 1:
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:560:7:
            // ( DIGIT )+ DOT ( DIGIT )* EXPONENT
            {
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:560:7:
                // ( DIGIT )+
                int cnt12 = 0;
                loop12: while (true) {
                    int alt12 = 2;
                    int LA12_0 = input.LA(1);
                    if (((LA12_0 >= '0' && LA12_0 <= '9'))) {
                        alt12 = 1;
                    }

                    switch (alt12) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        if (cnt12 >= 1)
                            break loop12;
                        EarlyExitException eee = new EarlyExitException(12,
                                input);
                        throw eee;
                    }
                    cnt12++;
                }

                mDOT();

                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:560:18:
                // ( DIGIT )*
                loop13: while (true) {
                    int alt13 = 2;
                    int LA13_0 = input.LA(1);
                    if (((LA13_0 >= '0' && LA13_0 <= '9'))) {
                        alt13 = 1;
                    }

                    switch (alt13) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        break loop13;
                    }
                }

                mEXPONENT();

            }
                break;
            case 2:
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:561:7:
            // DOT ( DIGIT )+ EXPONENT
            {
                mDOT();

                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:561:11:
                // ( DIGIT )+
                int cnt14 = 0;
                loop14: while (true) {
                    int alt14 = 2;
                    int LA14_0 = input.LA(1);
                    if (((LA14_0 >= '0' && LA14_0 <= '9'))) {
                        alt14 = 1;
                    }

                    switch (alt14) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        if (cnt14 >= 1)
                            break loop14;
                        EarlyExitException eee = new EarlyExitException(14,
                                input);
                        throw eee;
                    }
                    cnt14++;
                }

                mEXPONENT();

            }
                break;
            case 3:
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:562:7:
            // ( DIGIT )+ EXPONENT
            {
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:562:7:
                // ( DIGIT )+
                int cnt15 = 0;
                loop15: while (true) {
                    int alt15 = 2;
                    int LA15_0 = input.LA(1);
                    if (((LA15_0 >= '0' && LA15_0 <= '9'))) {
                        alt15 = 1;
                    }

                    switch (alt15) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        if (cnt15 >= 1)
                            break loop15;
                        EarlyExitException eee = new EarlyExitException(15,
                                input);
                        throw eee;
                    }
                    cnt15++;
                }

                mEXPONENT();

            }
                break;

            }
            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DOUBLE"

    // $ANTLR start "INTEGER_POSITIVE"
    public final void mINTEGER_POSITIVE() throws RecognitionException {
        try {
            int _type = INTEGER_POSITIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:566:5:
            // ( PLUS INTEGER )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:566:7:
            // PLUS INTEGER
            {
                mPLUS();

                mINTEGER();

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "INTEGER_POSITIVE"

    // $ANTLR start "DECIMAL_POSITIVE"
    public final void mDECIMAL_POSITIVE() throws RecognitionException {
        try {
            int _type = DECIMAL_POSITIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:570:5:
            // ( PLUS DECIMAL )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:570:7:
            // PLUS DECIMAL
            {
                mPLUS();

                mDECIMAL();

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DECIMAL_POSITIVE"

    // $ANTLR start "DOUBLE_POSITIVE"
    public final void mDOUBLE_POSITIVE() throws RecognitionException {
        try {
            int _type = DOUBLE_POSITIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:574:5:
            // ( PLUS DOUBLE )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:574:7:
            // PLUS DOUBLE
            {
                mPLUS();

                mDOUBLE();

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DOUBLE_POSITIVE"

    // $ANTLR start "INTEGER_NEGATIVE"
    public final void mINTEGER_NEGATIVE() throws RecognitionException {
        try {
            int _type = INTEGER_NEGATIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:578:5:
            // ( MINUS INTEGER )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:578:7:
            // MINUS INTEGER
            {
                mMINUS();

                mINTEGER();

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "INTEGER_NEGATIVE"

    // $ANTLR start "DECIMAL_NEGATIVE"
    public final void mDECIMAL_NEGATIVE() throws RecognitionException {
        try {
            int _type = DECIMAL_NEGATIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:582:5:
            // ( MINUS DECIMAL )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:582:7:
            // MINUS DECIMAL
            {
                mMINUS();

                mDECIMAL();

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DECIMAL_NEGATIVE"

    // $ANTLR start "DOUBLE_NEGATIVE"
    public final void mDOUBLE_NEGATIVE() throws RecognitionException {
        try {
            int _type = DOUBLE_NEGATIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:586:5:
            // ( MINUS DOUBLE )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:586:7:
            // MINUS DOUBLE
            {
                mMINUS();

                mDOUBLE();

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DOUBLE_NEGATIVE"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:592:5:
            // ( ( 'e' | 'E' ) ( PLUS | MINUS )? ( DIGIT )+ )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:592:7:
            // ( 'e' | 'E' ) ( PLUS | MINUS )? ( DIGIT )+
            {
                if (input.LA(1) == 'E' || input.LA(1) == 'e') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:592:17:
                // ( PLUS | MINUS )?
                int alt17 = 2;
                int LA17_0 = input.LA(1);
                if ((LA17_0 == '+' || LA17_0 == '-')) {
                    alt17 = 1;
                }
                switch (alt17) {
                case 1:
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                {
                    if (input.LA(1) == '+' || input.LA(1) == '-') {
                        input.consume();
                    } else {
                        MismatchedSetException mse = new MismatchedSetException(
                                null, input);
                        recover(mse);
                        throw mse;
                    }
                }
                    break;

                }

                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:592:31:
                // ( DIGIT )+
                int cnt18 = 0;
                loop18: while (true) {
                    int alt18 = 2;
                    int LA18_0 = input.LA(1);
                    if (((LA18_0 >= '0' && LA18_0 <= '9'))) {
                        alt18 = 1;
                    }

                    switch (alt18) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        if (cnt18 >= 1)
                            break loop18;
                        EarlyExitException eee = new EarlyExitException(18,
                                input);
                        throw eee;
                    }
                    cnt18++;
                }

            }

        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "EXPONENT"

    // $ANTLR start "STRING_LITERAL1"
    public final void mSTRING_LITERAL1() throws RecognitionException {
        try {
            int _type = STRING_LITERAL1;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:595:5:
            // ( '\\'' ( options {greedy=false; } :~ ( '\\u0027' | '\\u005C' |
            // '\ ' | '\ ' ) | ECHAR )* '\\'' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:595:7:
            // '\\'' ( options {greedy=false; } :~ ( '\\u0027' | '\\u005C' | '\
            // ' | '\ ' ) | ECHAR )* '\\''
            {
                match('\'');
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:595:12:
                // ( options {greedy=false; } :~ ( '\\u0027' | '\\u005C' | '\ '
                // | '\ ' ) | ECHAR )*
                loop19: while (true) {
                    int alt19 = 3;
                    int LA19_0 = input.LA(1);
                    if (((LA19_0 >= '\u0000' && LA19_0 <= '\t')
                            || (LA19_0 >= '\u000B' && LA19_0 <= '\f')
                            || (LA19_0 >= '\u000E' && LA19_0 <= '&')
                            || (LA19_0 >= '(' && LA19_0 <= '[') || (LA19_0 >= ']' && LA19_0 <= '\uFFFF'))) {
                        alt19 = 1;
                    } else if ((LA19_0 == '\\')) {
                        alt19 = 2;
                    } else if ((LA19_0 == '\'')) {
                        alt19 = 3;
                    }

                    switch (alt19) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:595:40:
                    // ~ ( '\\u0027' | '\\u005C' | '\ ' | '\ ' )
                    {
                        if ((input.LA(1) >= '\u0000' && input.LA(1) <= '\t')
                                || (input.LA(1) >= '\u000B' && input.LA(1) <= '\f')
                                || (input.LA(1) >= '\u000E' && input.LA(1) <= '&')
                                || (input.LA(1) >= '(' && input.LA(1) <= '[')
                                || (input.LA(1) >= ']' && input.LA(1) <= '\uFFFF')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;
                    case 2:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:595:87:
                    // ECHAR
                    {
                        mECHAR();

                    }
                        break;

                    default:
                        break loop19;
                    }
                }

                match('\'');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "STRING_LITERAL1"

    // $ANTLR start "STRING_LITERAL2"
    public final void mSTRING_LITERAL2() throws RecognitionException {
        try {
            int _type = STRING_LITERAL2;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:599:5:
            // ( '\"' ( options {greedy=false; } :~ ( '\\u0022' | '\\u005C' | '\
            // ' | '\ ' ) | ECHAR )* '\"' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:599:7:
            // '\"' ( options {greedy=false; } :~ ( '\\u0022' | '\\u005C' | '\ '
            // | '\ ' ) | ECHAR )* '\"'
            {
                match('\"');
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:599:12:
                // ( options {greedy=false; } :~ ( '\\u0022' | '\\u005C' | '\ '
                // | '\ ' ) | ECHAR )*
                loop20: while (true) {
                    int alt20 = 3;
                    int LA20_0 = input.LA(1);
                    if (((LA20_0 >= '\u0000' && LA20_0 <= '\t')
                            || (LA20_0 >= '\u000B' && LA20_0 <= '\f')
                            || (LA20_0 >= '\u000E' && LA20_0 <= '!')
                            || (LA20_0 >= '#' && LA20_0 <= '[') || (LA20_0 >= ']' && LA20_0 <= '\uFFFF'))) {
                        alt20 = 1;
                    } else if ((LA20_0 == '\\')) {
                        alt20 = 2;
                    } else if ((LA20_0 == '\"')) {
                        alt20 = 3;
                    }

                    switch (alt20) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:599:40:
                    // ~ ( '\\u0022' | '\\u005C' | '\ ' | '\ ' )
                    {
                        if ((input.LA(1) >= '\u0000' && input.LA(1) <= '\t')
                                || (input.LA(1) >= '\u000B' && input.LA(1) <= '\f')
                                || (input.LA(1) >= '\u000E' && input.LA(1) <= '!')
                                || (input.LA(1) >= '#' && input.LA(1) <= '[')
                                || (input.LA(1) >= ']' && input.LA(1) <= '\uFFFF')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;
                    case 2:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:599:87:
                    // ECHAR
                    {
                        mECHAR();

                    }
                        break;

                    default:
                        break loop20;
                    }
                }

                match('\"');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "STRING_LITERAL2"

    // $ANTLR start "STRING_LITERAL_LONG1"
    public final void mSTRING_LITERAL_LONG1() throws RecognitionException {
        try {
            int _type = STRING_LITERAL_LONG1;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:603:5:
            // ( '\\'\\'\\'' ( options {greedy=false; } : ( '\\'' | '\\'\\'' )?
            // (~ ( '\\'' | '\\\\' ) | ECHAR ) )* '\\'\\'\\'' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:603:9:
            // '\\'\\'\\'' ( options {greedy=false; } : ( '\\'' | '\\'\\'' )? (~
            // ( '\\'' | '\\\\' ) | ECHAR ) )* '\\'\\'\\''
            {
                match("'''");

                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:603:18:
                // ( options {greedy=false; } : ( '\\'' | '\\'\\'' )? (~ ( '\\''
                // | '\\\\' ) | ECHAR ) )*
                loop23: while (true) {
                    int alt23 = 2;
                    int LA23_0 = input.LA(1);
                    if ((LA23_0 == '\'')) {
                        int LA23_1 = input.LA(2);
                        if ((LA23_1 == '\'')) {
                            int LA23_3 = input.LA(3);
                            if ((LA23_3 == '\'')) {
                                alt23 = 2;
                            } else if (((LA23_3 >= '\u0000' && LA23_3 <= '&') || (LA23_3 >= '(' && LA23_3 <= '\uFFFF'))) {
                                alt23 = 1;
                            }

                        } else if (((LA23_1 >= '\u0000' && LA23_1 <= '&') || (LA23_1 >= '(' && LA23_1 <= '\uFFFF'))) {
                            alt23 = 1;
                        }

                    } else if (((LA23_0 >= '\u0000' && LA23_0 <= '&') || (LA23_0 >= '(' && LA23_0 <= '\uFFFF'))) {
                        alt23 = 1;
                    }

                    switch (alt23) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:603:46:
                    // ( '\\'' | '\\'\\'' )? (~ ( '\\'' | '\\\\' ) | ECHAR )
                    {
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:603:46:
                        // ( '\\'' | '\\'\\'' )?
                        int alt21 = 3;
                        int LA21_0 = input.LA(1);
                        if ((LA21_0 == '\'')) {
                            int LA21_1 = input.LA(2);
                            if ((LA21_1 == '\'')) {
                                alt21 = 2;
                            } else if (((LA21_1 >= '\u0000' && LA21_1 <= '&') || (LA21_1 >= '(' && LA21_1 <= '\uFFFF'))) {
                                alt21 = 1;
                            }
                        }
                        switch (alt21) {
                        case 1:
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:603:48:
                        // '\\''
                        {
                            match('\'');
                        }
                            break;
                        case 2:
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:603:55:
                        // '\\'\\''
                        {
                            match("''");

                        }
                            break;

                        }

                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:603:65:
                        // (~ ( '\\'' | '\\\\' ) | ECHAR )
                        int alt22 = 2;
                        int LA22_0 = input.LA(1);
                        if (((LA22_0 >= '\u0000' && LA22_0 <= '&')
                                || (LA22_0 >= '(' && LA22_0 <= '[') || (LA22_0 >= ']' && LA22_0 <= '\uFFFF'))) {
                            alt22 = 1;
                        } else if ((LA22_0 == '\\')) {
                            alt22 = 2;
                        }

                        else {
                            NoViableAltException nvae = new NoViableAltException(
                                    "", 22, 0, input);
                            throw nvae;
                        }

                        switch (alt22) {
                        case 1:
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:603:67:
                        // ~ ( '\\'' | '\\\\' )
                        {
                            if ((input.LA(1) >= '\u0000' && input.LA(1) <= '&')
                                    || (input.LA(1) >= '(' && input.LA(1) <= '[')
                                    || (input.LA(1) >= ']' && input.LA(1) <= '\uFFFF')) {
                                input.consume();
                            } else {
                                MismatchedSetException mse = new MismatchedSetException(
                                        null, input);
                                recover(mse);
                                throw mse;
                            }
                        }
                            break;
                        case 2:
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:603:82:
                        // ECHAR
                        {
                            mECHAR();

                        }
                            break;

                        }

                    }
                        break;

                    default:
                        break loop23;
                    }
                }

                match("'''");

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "STRING_LITERAL_LONG1"

    // $ANTLR start "STRING_LITERAL_LONG2"
    public final void mSTRING_LITERAL_LONG2() throws RecognitionException {
        try {
            int _type = STRING_LITERAL_LONG2;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:607:5:
            // ( '\"\"\"' ( options {greedy=false; } : ( '\"' | '\"\"' )? (~ (
            // '\"' | '\\\\' ) | ECHAR ) )* '\"\"\"' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:607:9:
            // '\"\"\"' ( options {greedy=false; } : ( '\"' | '\"\"' )? (~ (
            // '\"' | '\\\\' ) | ECHAR ) )* '\"\"\"'
            {
                match("\"\"\"");

                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:607:15:
                // ( options {greedy=false; } : ( '\"' | '\"\"' )? (~ ( '\"' |
                // '\\\\' ) | ECHAR ) )*
                loop26: while (true) {
                    int alt26 = 2;
                    int LA26_0 = input.LA(1);
                    if ((LA26_0 == '\"')) {
                        int LA26_1 = input.LA(2);
                        if ((LA26_1 == '\"')) {
                            int LA26_3 = input.LA(3);
                            if ((LA26_3 == '\"')) {
                                alt26 = 2;
                            } else if (((LA26_3 >= '\u0000' && LA26_3 <= '!') || (LA26_3 >= '#' && LA26_3 <= '\uFFFF'))) {
                                alt26 = 1;
                            }

                        } else if (((LA26_1 >= '\u0000' && LA26_1 <= '!') || (LA26_1 >= '#' && LA26_1 <= '\uFFFF'))) {
                            alt26 = 1;
                        }

                    } else if (((LA26_0 >= '\u0000' && LA26_0 <= '!') || (LA26_0 >= '#' && LA26_0 <= '\uFFFF'))) {
                        alt26 = 1;
                    }

                    switch (alt26) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:607:43:
                    // ( '\"' | '\"\"' )? (~ ( '\"' | '\\\\' ) | ECHAR )
                    {
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:607:43:
                        // ( '\"' | '\"\"' )?
                        int alt24 = 3;
                        int LA24_0 = input.LA(1);
                        if ((LA24_0 == '\"')) {
                            int LA24_1 = input.LA(2);
                            if ((LA24_1 == '\"')) {
                                alt24 = 2;
                            } else if (((LA24_1 >= '\u0000' && LA24_1 <= '!') || (LA24_1 >= '#' && LA24_1 <= '\uFFFF'))) {
                                alt24 = 1;
                            }
                        }
                        switch (alt24) {
                        case 1:
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:607:45:
                        // '\"'
                        {
                            match('\"');
                        }
                            break;
                        case 2:
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:607:51:
                        // '\"\"'
                        {
                            match("\"\"");

                        }
                            break;

                        }

                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:607:59:
                        // (~ ( '\"' | '\\\\' ) | ECHAR )
                        int alt25 = 2;
                        int LA25_0 = input.LA(1);
                        if (((LA25_0 >= '\u0000' && LA25_0 <= '!')
                                || (LA25_0 >= '#' && LA25_0 <= '[') || (LA25_0 >= ']' && LA25_0 <= '\uFFFF'))) {
                            alt25 = 1;
                        } else if ((LA25_0 == '\\')) {
                            alt25 = 2;
                        }

                        else {
                            NoViableAltException nvae = new NoViableAltException(
                                    "", 25, 0, input);
                            throw nvae;
                        }

                        switch (alt25) {
                        case 1:
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:607:61:
                        // ~ ( '\"' | '\\\\' )
                        {
                            if ((input.LA(1) >= '\u0000' && input.LA(1) <= '!')
                                    || (input.LA(1) >= '#' && input.LA(1) <= '[')
                                    || (input.LA(1) >= ']' && input.LA(1) <= '\uFFFF')) {
                                input.consume();
                            } else {
                                MismatchedSetException mse = new MismatchedSetException(
                                        null, input);
                                recover(mse);
                                throw mse;
                            }
                        }
                            break;
                        case 2:
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:607:75:
                        // ECHAR
                        {
                            mECHAR();

                        }
                            break;

                        }

                    }
                        break;

                    default:
                        break loop26;
                    }
                }

                match("\"\"\"");

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "STRING_LITERAL_LONG2"

    // $ANTLR start "ECHAR"
    public final void mECHAR() throws RecognitionException {
        try {
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:613:5:
            // ( '\\\\' ( 't' | 'b' | 'n' | 'r' | 'f' | '\\\\' | '\"' | '\\'' )
            // )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:613:7:
            // '\\\\' ( 't' | 'b' | 'n' | 'r' | 'f' | '\\\\' | '\"' | '\\'' )
            {
                match('\\');
                if (input.LA(1) == '\"' || input.LA(1) == '\''
                        || input.LA(1) == '\\' || input.LA(1) == 'b'
                        || input.LA(1) == 'f' || input.LA(1) == 'n'
                        || input.LA(1) == 'r' || input.LA(1) == 't') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "ECHAR"

    // $ANTLR start "PN_CHARS_U"
    public final void mPN_CHARS_U() throws RecognitionException {
        try {
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:618:5:
            // ( PN_CHARS_BASE | '_' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
            {
                if ((input.LA(1) >= 'A' && input.LA(1) <= 'Z')
                        || input.LA(1) == '_'
                        || (input.LA(1) >= 'a' && input.LA(1) <= 'z')
                        || (input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')
                        || (input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')
                        || (input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')
                        || (input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')
                        || (input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')
                        || (input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')
                        || (input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')
                        || (input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')
                        || (input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')
                        || (input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')
                        || (input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD')) {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "PN_CHARS_U"

    // $ANTLR start "VARNAME"
    public final void mVARNAME() throws RecognitionException {
        try {
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:623:5:
            // ( ( PN_CHARS_U | DIGIT ) ( PN_CHARS_U | DIGIT | '\\u00B7' |
            // '\\u0300' .. '\\u036F' | '\\u203F' .. '\\u2040' )* )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:623:7:
            // ( PN_CHARS_U | DIGIT ) ( PN_CHARS_U | DIGIT | '\\u00B7' |
            // '\\u0300' .. '\\u036F' | '\\u203F' .. '\\u2040' )*
            {
                if ((input.LA(1) >= '0' && input.LA(1) <= '9')
                        || (input.LA(1) >= 'A' && input.LA(1) <= 'Z')
                        || input.LA(1) == '_'
                        || (input.LA(1) >= 'a' && input.LA(1) <= 'z')
                        || (input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')
                        || (input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')
                        || (input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')
                        || (input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')
                        || (input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')
                        || (input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')
                        || (input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')
                        || (input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')
                        || (input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')
                        || (input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')
                        || (input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD')) {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:623:30:
                // ( PN_CHARS_U | DIGIT | '\\u00B7' | '\\u0300' .. '\\u036F' |
                // '\\u203F' .. '\\u2040' )*
                loop27: while (true) {
                    int alt27 = 2;
                    int LA27_0 = input.LA(1);
                    if (((LA27_0 >= '0' && LA27_0 <= '9')
                            || (LA27_0 >= 'A' && LA27_0 <= 'Z')
                            || LA27_0 == '_'
                            || (LA27_0 >= 'a' && LA27_0 <= 'z')
                            || LA27_0 == '\u00B7'
                            || (LA27_0 >= '\u00C0' && LA27_0 <= '\u00D6')
                            || (LA27_0 >= '\u00D8' && LA27_0 <= '\u00F6')
                            || (LA27_0 >= '\u00F8' && LA27_0 <= '\u037D')
                            || (LA27_0 >= '\u037F' && LA27_0 <= '\u1FFF')
                            || (LA27_0 >= '\u200C' && LA27_0 <= '\u200D')
                            || (LA27_0 >= '\u203F' && LA27_0 <= '\u2040')
                            || (LA27_0 >= '\u2070' && LA27_0 <= '\u218F')
                            || (LA27_0 >= '\u2C00' && LA27_0 <= '\u2FEF')
                            || (LA27_0 >= '\u3001' && LA27_0 <= '\uD7FF')
                            || (LA27_0 >= '\uF900' && LA27_0 <= '\uFDCF') || (LA27_0 >= '\uFDF0' && LA27_0 <= '\uFFFD'))) {
                        alt27 = 1;
                    }

                    switch (alt27) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                    {
                        if ((input.LA(1) >= '0' && input.LA(1) <= '9')
                                || (input.LA(1) >= 'A' && input.LA(1) <= 'Z')
                                || input.LA(1) == '_'
                                || (input.LA(1) >= 'a' && input.LA(1) <= 'z')
                                || input.LA(1) == '\u00B7'
                                || (input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')
                                || (input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')
                                || (input.LA(1) >= '\u00F8' && input.LA(1) <= '\u037D')
                                || (input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')
                                || (input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')
                                || (input.LA(1) >= '\u203F' && input.LA(1) <= '\u2040')
                                || (input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')
                                || (input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')
                                || (input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')
                                || (input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')
                                || (input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD')) {
                            input.consume();
                        } else {
                            MismatchedSetException mse = new MismatchedSetException(
                                    null, input);
                            recover(mse);
                            throw mse;
                        }
                    }
                        break;

                    default:
                        break loop27;
                    }
                }

            }

        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "VARNAME"

    // $ANTLR start "PN_CHARS"
    public final void mPN_CHARS() throws RecognitionException {
        try {
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:628:5:
            // ( PN_CHARS_U | MINUS | DIGIT | '\\u00B7' | '\\u0300' .. '\\u036F'
            // | '\\u203F' .. '\\u2040' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
            {
                if (input.LA(1) == '-'
                        || (input.LA(1) >= '0' && input.LA(1) <= '9')
                        || (input.LA(1) >= 'A' && input.LA(1) <= 'Z')
                        || input.LA(1) == '_'
                        || (input.LA(1) >= 'a' && input.LA(1) <= 'z')
                        || input.LA(1) == '\u00B7'
                        || (input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')
                        || (input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')
                        || (input.LA(1) >= '\u00F8' && input.LA(1) <= '\u037D')
                        || (input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')
                        || (input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')
                        || (input.LA(1) >= '\u203F' && input.LA(1) <= '\u2040')
                        || (input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')
                        || (input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')
                        || (input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')
                        || (input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')
                        || (input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD')) {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "PN_CHARS"

    // $ANTLR start "PN_PREFIX"
    public final void mPN_PREFIX() throws RecognitionException {
        try {
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:638:5:
            // ( PN_CHARS_BASE ( ( PN_CHARS | DOT )* PN_CHARS )? )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:638:7:
            // PN_CHARS_BASE ( ( PN_CHARS | DOT )* PN_CHARS )?
            {
                mPN_CHARS_BASE();

                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:638:21:
                // ( ( PN_CHARS | DOT )* PN_CHARS )?
                int alt29 = 2;
                int LA29_0 = input.LA(1);
                if (((LA29_0 >= '-' && LA29_0 <= '.')
                        || (LA29_0 >= '0' && LA29_0 <= '9')
                        || (LA29_0 >= 'A' && LA29_0 <= 'Z') || LA29_0 == '_'
                        || (LA29_0 >= 'a' && LA29_0 <= 'z')
                        || LA29_0 == '\u00B7'
                        || (LA29_0 >= '\u00C0' && LA29_0 <= '\u00D6')
                        || (LA29_0 >= '\u00D8' && LA29_0 <= '\u00F6')
                        || (LA29_0 >= '\u00F8' && LA29_0 <= '\u037D')
                        || (LA29_0 >= '\u037F' && LA29_0 <= '\u1FFF')
                        || (LA29_0 >= '\u200C' && LA29_0 <= '\u200D')
                        || (LA29_0 >= '\u203F' && LA29_0 <= '\u2040')
                        || (LA29_0 >= '\u2070' && LA29_0 <= '\u218F')
                        || (LA29_0 >= '\u2C00' && LA29_0 <= '\u2FEF')
                        || (LA29_0 >= '\u3001' && LA29_0 <= '\uD7FF')
                        || (LA29_0 >= '\uF900' && LA29_0 <= '\uFDCF') || (LA29_0 >= '\uFDF0' && LA29_0 <= '\uFFFD'))) {
                    alt29 = 1;
                }
                switch (alt29) {
                case 1:
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:638:22:
                // ( PN_CHARS | DOT )* PN_CHARS
                {
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:638:22:
                    // ( PN_CHARS | DOT )*
                    loop28: while (true) {
                        int alt28 = 2;
                        int LA28_0 = input.LA(1);
                        if ((LA28_0 == '-' || (LA28_0 >= '0' && LA28_0 <= '9')
                                || (LA28_0 >= 'A' && LA28_0 <= 'Z')
                                || LA28_0 == '_'
                                || (LA28_0 >= 'a' && LA28_0 <= 'z')
                                || LA28_0 == '\u00B7'
                                || (LA28_0 >= '\u00C0' && LA28_0 <= '\u00D6')
                                || (LA28_0 >= '\u00D8' && LA28_0 <= '\u00F6')
                                || (LA28_0 >= '\u00F8' && LA28_0 <= '\u037D')
                                || (LA28_0 >= '\u037F' && LA28_0 <= '\u1FFF')
                                || (LA28_0 >= '\u200C' && LA28_0 <= '\u200D')
                                || (LA28_0 >= '\u203F' && LA28_0 <= '\u2040')
                                || (LA28_0 >= '\u2070' && LA28_0 <= '\u218F')
                                || (LA28_0 >= '\u2C00' && LA28_0 <= '\u2FEF')
                                || (LA28_0 >= '\u3001' && LA28_0 <= '\uD7FF')
                                || (LA28_0 >= '\uF900' && LA28_0 <= '\uFDCF') || (LA28_0 >= '\uFDF0' && LA28_0 <= '\uFFFD'))) {
                            int LA28_1 = input.LA(2);
                            if (((LA28_1 >= '-' && LA28_1 <= '.')
                                    || (LA28_1 >= '0' && LA28_1 <= '9')
                                    || (LA28_1 >= 'A' && LA28_1 <= 'Z')
                                    || LA28_1 == '_'
                                    || (LA28_1 >= 'a' && LA28_1 <= 'z')
                                    || LA28_1 == '\u00B7'
                                    || (LA28_1 >= '\u00C0' && LA28_1 <= '\u00D6')
                                    || (LA28_1 >= '\u00D8' && LA28_1 <= '\u00F6')
                                    || (LA28_1 >= '\u00F8' && LA28_1 <= '\u037D')
                                    || (LA28_1 >= '\u037F' && LA28_1 <= '\u1FFF')
                                    || (LA28_1 >= '\u200C' && LA28_1 <= '\u200D')
                                    || (LA28_1 >= '\u203F' && LA28_1 <= '\u2040')
                                    || (LA28_1 >= '\u2070' && LA28_1 <= '\u218F')
                                    || (LA28_1 >= '\u2C00' && LA28_1 <= '\u2FEF')
                                    || (LA28_1 >= '\u3001' && LA28_1 <= '\uD7FF')
                                    || (LA28_1 >= '\uF900' && LA28_1 <= '\uFDCF') || (LA28_1 >= '\uFDF0' && LA28_1 <= '\uFFFD'))) {
                                alt28 = 1;
                            }

                        } else if ((LA28_0 == '.')) {
                            alt28 = 1;
                        }

                        switch (alt28) {
                        case 1:
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                        {
                            if ((input.LA(1) >= '-' && input.LA(1) <= '.')
                                    || (input.LA(1) >= '0' && input.LA(1) <= '9')
                                    || (input.LA(1) >= 'A' && input.LA(1) <= 'Z')
                                    || input.LA(1) == '_'
                                    || (input.LA(1) >= 'a' && input.LA(1) <= 'z')
                                    || input.LA(1) == '\u00B7'
                                    || (input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')
                                    || (input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')
                                    || (input.LA(1) >= '\u00F8' && input.LA(1) <= '\u037D')
                                    || (input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')
                                    || (input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')
                                    || (input.LA(1) >= '\u203F' && input.LA(1) <= '\u2040')
                                    || (input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')
                                    || (input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')
                                    || (input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')
                                    || (input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')
                                    || (input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD')) {
                                input.consume();
                            } else {
                                MismatchedSetException mse = new MismatchedSetException(
                                        null, input);
                                recover(mse);
                                throw mse;
                            }
                        }
                            break;

                        default:
                            break loop28;
                        }
                    }

                    mPN_CHARS();

                }
                    break;

                }

            }

        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "PN_PREFIX"

    // $ANTLR start "PN_LOCAL"
    public final void mPN_LOCAL() throws RecognitionException {
        try {
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:643:5:
            // ( ( PN_CHARS_U | DIGIT ) ( ( PN_CHARS | DOT )* PN_CHARS )? )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:643:7:
            // ( PN_CHARS_U | DIGIT ) ( ( PN_CHARS | DOT )* PN_CHARS )?
            {
                if ((input.LA(1) >= '0' && input.LA(1) <= '9')
                        || (input.LA(1) >= 'A' && input.LA(1) <= 'Z')
                        || input.LA(1) == '_'
                        || (input.LA(1) >= 'a' && input.LA(1) <= 'z')
                        || (input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')
                        || (input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')
                        || (input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')
                        || (input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')
                        || (input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')
                        || (input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')
                        || (input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')
                        || (input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')
                        || (input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')
                        || (input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')
                        || (input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD')) {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:643:30:
                // ( ( PN_CHARS | DOT )* PN_CHARS )?
                int alt31 = 2;
                int LA31_0 = input.LA(1);
                if (((LA31_0 >= '-' && LA31_0 <= '.')
                        || (LA31_0 >= '0' && LA31_0 <= '9')
                        || (LA31_0 >= 'A' && LA31_0 <= 'Z') || LA31_0 == '_'
                        || (LA31_0 >= 'a' && LA31_0 <= 'z')
                        || LA31_0 == '\u00B7'
                        || (LA31_0 >= '\u00C0' && LA31_0 <= '\u00D6')
                        || (LA31_0 >= '\u00D8' && LA31_0 <= '\u00F6')
                        || (LA31_0 >= '\u00F8' && LA31_0 <= '\u037D')
                        || (LA31_0 >= '\u037F' && LA31_0 <= '\u1FFF')
                        || (LA31_0 >= '\u200C' && LA31_0 <= '\u200D')
                        || (LA31_0 >= '\u203F' && LA31_0 <= '\u2040')
                        || (LA31_0 >= '\u2070' && LA31_0 <= '\u218F')
                        || (LA31_0 >= '\u2C00' && LA31_0 <= '\u2FEF')
                        || (LA31_0 >= '\u3001' && LA31_0 <= '\uD7FF')
                        || (LA31_0 >= '\uF900' && LA31_0 <= '\uFDCF') || (LA31_0 >= '\uFDF0' && LA31_0 <= '\uFFFD'))) {
                    alt31 = 1;
                }
                switch (alt31) {
                case 1:
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:643:31:
                // ( PN_CHARS | DOT )* PN_CHARS
                {
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:643:31:
                    // ( PN_CHARS | DOT )*
                    loop30: while (true) {
                        int alt30 = 2;
                        int LA30_0 = input.LA(1);
                        if ((LA30_0 == '-' || (LA30_0 >= '0' && LA30_0 <= '9')
                                || (LA30_0 >= 'A' && LA30_0 <= 'Z')
                                || LA30_0 == '_'
                                || (LA30_0 >= 'a' && LA30_0 <= 'z')
                                || LA30_0 == '\u00B7'
                                || (LA30_0 >= '\u00C0' && LA30_0 <= '\u00D6')
                                || (LA30_0 >= '\u00D8' && LA30_0 <= '\u00F6')
                                || (LA30_0 >= '\u00F8' && LA30_0 <= '\u037D')
                                || (LA30_0 >= '\u037F' && LA30_0 <= '\u1FFF')
                                || (LA30_0 >= '\u200C' && LA30_0 <= '\u200D')
                                || (LA30_0 >= '\u203F' && LA30_0 <= '\u2040')
                                || (LA30_0 >= '\u2070' && LA30_0 <= '\u218F')
                                || (LA30_0 >= '\u2C00' && LA30_0 <= '\u2FEF')
                                || (LA30_0 >= '\u3001' && LA30_0 <= '\uD7FF')
                                || (LA30_0 >= '\uF900' && LA30_0 <= '\uFDCF') || (LA30_0 >= '\uFDF0' && LA30_0 <= '\uFFFD'))) {
                            int LA30_1 = input.LA(2);
                            if (((LA30_1 >= '-' && LA30_1 <= '.')
                                    || (LA30_1 >= '0' && LA30_1 <= '9')
                                    || (LA30_1 >= 'A' && LA30_1 <= 'Z')
                                    || LA30_1 == '_'
                                    || (LA30_1 >= 'a' && LA30_1 <= 'z')
                                    || LA30_1 == '\u00B7'
                                    || (LA30_1 >= '\u00C0' && LA30_1 <= '\u00D6')
                                    || (LA30_1 >= '\u00D8' && LA30_1 <= '\u00F6')
                                    || (LA30_1 >= '\u00F8' && LA30_1 <= '\u037D')
                                    || (LA30_1 >= '\u037F' && LA30_1 <= '\u1FFF')
                                    || (LA30_1 >= '\u200C' && LA30_1 <= '\u200D')
                                    || (LA30_1 >= '\u203F' && LA30_1 <= '\u2040')
                                    || (LA30_1 >= '\u2070' && LA30_1 <= '\u218F')
                                    || (LA30_1 >= '\u2C00' && LA30_1 <= '\u2FEF')
                                    || (LA30_1 >= '\u3001' && LA30_1 <= '\uD7FF')
                                    || (LA30_1 >= '\uF900' && LA30_1 <= '\uFDCF') || (LA30_1 >= '\uFDF0' && LA30_1 <= '\uFFFD'))) {
                                alt30 = 1;
                            }

                        } else if ((LA30_0 == '.')) {
                            alt30 = 1;
                        }

                        switch (alt30) {
                        case 1:
                        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
                        {
                            if ((input.LA(1) >= '-' && input.LA(1) <= '.')
                                    || (input.LA(1) >= '0' && input.LA(1) <= '9')
                                    || (input.LA(1) >= 'A' && input.LA(1) <= 'Z')
                                    || input.LA(1) == '_'
                                    || (input.LA(1) >= 'a' && input.LA(1) <= 'z')
                                    || input.LA(1) == '\u00B7'
                                    || (input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')
                                    || (input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')
                                    || (input.LA(1) >= '\u00F8' && input.LA(1) <= '\u037D')
                                    || (input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')
                                    || (input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')
                                    || (input.LA(1) >= '\u203F' && input.LA(1) <= '\u2040')
                                    || (input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')
                                    || (input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')
                                    || (input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')
                                    || (input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')
                                    || (input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD')) {
                                input.consume();
                            } else {
                                MismatchedSetException mse = new MismatchedSetException(
                                        null, input);
                                recover(mse);
                                throw mse;
                            }
                        }
                            break;

                        default:
                            break loop30;
                        }
                    }

                    mPN_CHARS();

                }
                    break;

                }

            }

        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "PN_LOCAL"

    // $ANTLR start "PN_CHARS_BASE"
    public final void mPN_CHARS_BASE() throws RecognitionException {
        try {
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:648:5:
            // ( 'A' .. 'Z' | 'a' .. 'z' | '\\u00C0' .. '\\u00D6' | '\\u00D8' ..
            // '\\u00F6' | '\\u00F8' .. '\\u02FF' | '\\u0370' .. '\\u037D' |
            // '\\u037F' .. '\\u1FFF' | '\\u200C' .. '\\u200D' | '\\u2070' ..
            // '\\u218F' | '\\u2C00' .. '\\u2FEF' | '\\u3001' .. '\\uD7FF' |
            // '\\uF900' .. '\\uFDCF' | '\\uFDF0' .. '\\uFFFD' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
            {
                if ((input.LA(1) >= 'A' && input.LA(1) <= 'Z')
                        || (input.LA(1) >= 'a' && input.LA(1) <= 'z')
                        || (input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')
                        || (input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')
                        || (input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')
                        || (input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')
                        || (input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')
                        || (input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')
                        || (input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')
                        || (input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')
                        || (input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')
                        || (input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')
                        || (input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD')) {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "PN_CHARS_BASE"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:665:5:
            // ( '0' .. '9' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
            {
                if ((input.LA(1) >= '0' && input.LA(1) <= '9')) {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DIGIT"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:668:5:
            // ( '#' ( options {greedy=false; } : . )* EOL )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:668:7:
            // '#' ( options {greedy=false; } : . )* EOL
            {
                match('#');
                // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:668:11:
                // ( options {greedy=false; } : . )*
                loop32: while (true) {
                    int alt32 = 2;
                    int LA32_0 = input.LA(1);
                    if ((LA32_0 == '\n' || LA32_0 == '\r')) {
                        alt32 = 2;
                    } else if (((LA32_0 >= '\u0000' && LA32_0 <= '\t')
                            || (LA32_0 >= '\u000B' && LA32_0 <= '\f') || (LA32_0 >= '\u000E' && LA32_0 <= '\uFFFF'))) {
                        alt32 = 1;
                    }

                    switch (alt32) {
                    case 1:
                    // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:668:38:
                    // .
                    {
                        matchAny();
                    }
                        break;

                    default:
                        break loop32;
                    }
                }

                mEOL();

                _channel = HIDDEN;
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "COMMENT"

    // $ANTLR start "EOL"
    public final void mEOL() throws RecognitionException {
        try {
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:674:5:
            // ( '\\n' | '\\r' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:
            {
                if (input.LA(1) == '\n' || input.LA(1) == '\r') {
                    input.consume();
                } else {
                    MismatchedSetException mse = new MismatchedSetException(
                            null, input);
                    recover(mse);
                    throw mse;
                }
            }

        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "EOL"

    // $ANTLR start "REFERENCE"
    public final void mREFERENCE() throws RecognitionException {
        try {
            int _type = REFERENCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:677:5:
            // ( '^^' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:677:7:
            // '^^'
            {
                match("^^");

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "REFERENCE"

    // $ANTLR start "LESS_EQUAL"
    public final void mLESS_EQUAL() throws RecognitionException {
        try {
            int _type = LESS_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:681:5:
            // ( '<=' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:681:7:
            // '<='
            {
                match("<=");

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "LESS_EQUAL"

    // $ANTLR start "GREATER_EQUAL"
    public final void mGREATER_EQUAL() throws RecognitionException {
        try {
            int _type = GREATER_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:685:5:
            // ( '>=' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:685:7:
            // '>='
            {
                match(">=");

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "GREATER_EQUAL"

    // $ANTLR start "NOT_EQUAL"
    public final void mNOT_EQUAL() throws RecognitionException {
        try {
            int _type = NOT_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:689:5:
            // ( '!=' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:689:7:
            // '!='
            {
                match("!=");

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "NOT_EQUAL"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:693:5:
            // ( '&&' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:693:7:
            // '&&'
            {
                match("&&");

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "AND"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:697:5:
            // ( '||' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:697:7:
            // '||'
            {
                match("||");

            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "OR"

    // $ANTLR start "OPEN_BRACE"
    public final void mOPEN_BRACE() throws RecognitionException {
        try {
            int _type = OPEN_BRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:701:5:
            // ( '(' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:701:7:
            // '('
            {
                match('(');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "OPEN_BRACE"

    // $ANTLR start "CLOSE_BRACE"
    public final void mCLOSE_BRACE() throws RecognitionException {
        try {
            int _type = CLOSE_BRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:705:5:
            // ( ')' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:705:7:
            // ')'
            {
                match(')');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "CLOSE_BRACE"

    // $ANTLR start "OPEN_CURLY_BRACE"
    public final void mOPEN_CURLY_BRACE() throws RecognitionException {
        try {
            int _type = OPEN_CURLY_BRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:709:5:
            // ( '{' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:709:7:
            // '{'
            {
                match('{');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "OPEN_CURLY_BRACE"

    // $ANTLR start "CLOSE_CURLY_BRACE"
    public final void mCLOSE_CURLY_BRACE() throws RecognitionException {
        try {
            int _type = CLOSE_CURLY_BRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:713:5:
            // ( '}' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:713:7:
            // '}'
            {
                match('}');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "CLOSE_CURLY_BRACE"

    // $ANTLR start "OPEN_SQUARE_BRACE"
    public final void mOPEN_SQUARE_BRACE() throws RecognitionException {
        try {
            int _type = OPEN_SQUARE_BRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:717:5:
            // ( '[' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:717:7:
            // '['
            {
                match('[');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "OPEN_SQUARE_BRACE"

    // $ANTLR start "CLOSE_SQUARE_BRACE"
    public final void mCLOSE_SQUARE_BRACE() throws RecognitionException {
        try {
            int _type = CLOSE_SQUARE_BRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:721:5:
            // ( ']' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:721:7:
            // ']'
            {
                match(']');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "CLOSE_SQUARE_BRACE"

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:725:5:
            // ( ';' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:725:7:
            // ';'
            {
                match(';');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "SEMICOLON"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:729:5:
            // ( '.' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:729:7:
            // '.'
            {
                match('.');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DOT"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:733:5:
            // ( '+' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:733:7:
            // '+'
            {
                match('+');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:737:5:
            // ( '-' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:737:7:
            // '-'
            {
                match('-');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "MINUS"

    // $ANTLR start "ASTERISK"
    public final void mASTERISK() throws RecognitionException {
        try {
            int _type = ASTERISK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:741:5:
            // ( '*' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:741:7:
            // '*'
            {
                match('*');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "ASTERISK"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:745:5:
            // ( ',' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:745:7:
            // ','
            {
                match(',');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "COMMA"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:749:5:
            // ( '!' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:749:7:
            // '!'
            {
                match('!');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "NOT"

    // $ANTLR start "DIVIDE"
    public final void mDIVIDE() throws RecognitionException {
        try {
            int _type = DIVIDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:753:5:
            // ( '/' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:753:7:
            // '/'
            {
                match('/');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "DIVIDE"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:757:5:
            // ( '=' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:757:7:
            // '='
            {
                match('=');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "EQUAL"

    // $ANTLR start "LESS"
    public final void mLESS() throws RecognitionException {
        try {
            int _type = LESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:761:5:
            // ( '<' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:761:7:
            // '<'
            {
                match('<');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "LESS"

    // $ANTLR start "GREATER"
    public final void mGREATER() throws RecognitionException {
        try {
            int _type = GREATER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:765:5:
            // ( '>' )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:765:7:
            // '>'
            {
                match('>');
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "GREATER"

    // $ANTLR start "ANY"
    public final void mANY() throws RecognitionException {
        try {
            int _type = ANY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:768:5:
            // ( . )
            // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:768:7:
            // .
            {
                matchAny();
            }

            state.type = _type;
            state.channel = _channel;
        } finally {
            // do for sure before leaving
        }
    }

    // $ANTLR end "ANY"

    @Override
    public void mTokens() throws RecognitionException {
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:8:
        // ( WS | PNAME_NS | PNAME_LN | BASE | PREFIX | SELECT | DISTINCT |
        // REDUCED | CONSTRUCT | DESCRIBE | ASK | FROM | NAMED | WHERE | ORDER |
        // BY | ASC | DESC | LIMIT | OFFSET | OPTIONAL | GRAPH | UNION | FILTER
        // | A | STR | LANG | LANGMATCHES | DATATYPE | BOUND | SAMETERM | ISIRI
        // | ISURI | ISBLANK | ISLITERAL | REGEX | TRUE | FALSE | IRI_REF |
        // BLANK_NODE_LABEL | VAR1 | VAR2 | LANGTAG | INTEGER | DECIMAL | DOUBLE
        // | INTEGER_POSITIVE | DECIMAL_POSITIVE | DOUBLE_POSITIVE |
        // INTEGER_NEGATIVE | DECIMAL_NEGATIVE | DOUBLE_NEGATIVE |
        // STRING_LITERAL1 | STRING_LITERAL2 | STRING_LITERAL_LONG1 |
        // STRING_LITERAL_LONG2 | COMMENT | REFERENCE | LESS_EQUAL |
        // GREATER_EQUAL | NOT_EQUAL | AND | OR | OPEN_BRACE | CLOSE_BRACE |
        // OPEN_CURLY_BRACE | CLOSE_CURLY_BRACE | OPEN_SQUARE_BRACE |
        // CLOSE_SQUARE_BRACE | SEMICOLON | DOT | PLUS | MINUS | ASTERISK |
        // COMMA | NOT | DIVIDE | EQUAL | LESS | GREATER | ANY )
        int alt33 = 81;
        alt33 = dfa33.predict(input);
        switch (alt33) {
        case 1:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:10:
        // WS
        {
            mWS();

        }
            break;
        case 2:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:13:
        // PNAME_NS
        {
            mPNAME_NS();

        }
            break;
        case 3:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:22:
        // PNAME_LN
        {
            mPNAME_LN();

        }
            break;
        case 4:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:31:
        // BASE
        {
            mBASE();

        }
            break;
        case 5:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:36:
        // PREFIX
        {
            mPREFIX();

        }
            break;
        case 6:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:43:
        // SELECT
        {
            mSELECT();

        }
            break;
        case 7:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:50:
        // DISTINCT
        {
            mDISTINCT();

        }
            break;
        case 8:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:59:
        // REDUCED
        {
            mREDUCED();

        }
            break;
        case 9:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:67:
        // CONSTRUCT
        {
            mCONSTRUCT();

        }
            break;
        case 10:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:77:
        // DESCRIBE
        {
            mDESCRIBE();

        }
            break;
        case 11:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:86:
        // ASK
        {
            mASK();

        }
            break;
        case 12:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:90:
        // FROM
        {
            mFROM();

        }
            break;
        case 13:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:95:
        // NAMED
        {
            mNAMED();

        }
            break;
        case 14:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:101:
        // WHERE
        {
            mWHERE();

        }
            break;
        case 15:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:107:
        // ORDER
        {
            mORDER();

        }
            break;
        case 16:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:113:
        // BY
        {
            mBY();

        }
            break;
        case 17:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:116:
        // ASC
        {
            mASC();

        }
            break;
        case 18:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:120:
        // DESC
        {
            mDESC();

        }
            break;
        case 19:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:125:
        // LIMIT
        {
            mLIMIT();

        }
            break;
        case 20:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:131:
        // OFFSET
        {
            mOFFSET();

        }
            break;
        case 21:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:138:
        // OPTIONAL
        {
            mOPTIONAL();

        }
            break;
        case 22:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:147:
        // GRAPH
        {
            mGRAPH();

        }
            break;
        case 23:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:153:
        // UNION
        {
            mUNION();

        }
            break;
        case 24:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:159:
        // FILTER
        {
            mFILTER();

        }
            break;
        case 25:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:166:
        // A
        {
            mA();

        }
            break;
        case 26:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:168:
        // STR
        {
            mSTR();

        }
            break;
        case 27:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:172:
        // LANG
        {
            mLANG();

        }
            break;
        case 28:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:177:
        // LANGMATCHES
        {
            mLANGMATCHES();

        }
            break;
        case 29:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:189:
        // DATATYPE
        {
            mDATATYPE();

        }
            break;
        case 30:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:198:
        // BOUND
        {
            mBOUND();

        }
            break;
        case 31:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:204:
        // SAMETERM
        {
            mSAMETERM();

        }
            break;
        case 32:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:213:
        // ISIRI
        {
            mISIRI();

        }
            break;
        case 33:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:219:
        // ISURI
        {
            mISURI();

        }
            break;
        case 34:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:225:
        // ISBLANK
        {
            mISBLANK();

        }
            break;
        case 35:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:233:
        // ISLITERAL
        {
            mISLITERAL();

        }
            break;
        case 36:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:243:
        // REGEX
        {
            mREGEX();

        }
            break;
        case 37:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:249:
        // TRUE
        {
            mTRUE();

        }
            break;
        case 38:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:254:
        // FALSE
        {
            mFALSE();

        }
            break;
        case 39:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:260:
        // IRI_REF
        {
            mIRI_REF();

        }
            break;
        case 40:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:268:
        // BLANK_NODE_LABEL
        {
            mBLANK_NODE_LABEL();

        }
            break;
        case 41:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:285:
        // VAR1
        {
            mVAR1();

        }
            break;
        case 42:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:290:
        // VAR2
        {
            mVAR2();

        }
            break;
        case 43:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:295:
        // LANGTAG
        {
            mLANGTAG();

        }
            break;
        case 44:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:303:
        // INTEGER
        {
            mINTEGER();

        }
            break;
        case 45:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:311:
        // DECIMAL
        {
            mDECIMAL();

        }
            break;
        case 46:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:319:
        // DOUBLE
        {
            mDOUBLE();

        }
            break;
        case 47:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:326:
        // INTEGER_POSITIVE
        {
            mINTEGER_POSITIVE();

        }
            break;
        case 48:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:343:
        // DECIMAL_POSITIVE
        {
            mDECIMAL_POSITIVE();

        }
            break;
        case 49:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:360:
        // DOUBLE_POSITIVE
        {
            mDOUBLE_POSITIVE();

        }
            break;
        case 50:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:376:
        // INTEGER_NEGATIVE
        {
            mINTEGER_NEGATIVE();

        }
            break;
        case 51:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:393:
        // DECIMAL_NEGATIVE
        {
            mDECIMAL_NEGATIVE();

        }
            break;
        case 52:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:410:
        // DOUBLE_NEGATIVE
        {
            mDOUBLE_NEGATIVE();

        }
            break;
        case 53:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:426:
        // STRING_LITERAL1
        {
            mSTRING_LITERAL1();

        }
            break;
        case 54:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:442:
        // STRING_LITERAL2
        {
            mSTRING_LITERAL2();

        }
            break;
        case 55:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:458:
        // STRING_LITERAL_LONG1
        {
            mSTRING_LITERAL_LONG1();

        }
            break;
        case 56:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:479:
        // STRING_LITERAL_LONG2
        {
            mSTRING_LITERAL_LONG2();

        }
            break;
        case 57:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:500:
        // COMMENT
        {
            mCOMMENT();

        }
            break;
        case 58:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:508:
        // REFERENCE
        {
            mREFERENCE();

        }
            break;
        case 59:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:518:
        // LESS_EQUAL
        {
            mLESS_EQUAL();

        }
            break;
        case 60:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:529:
        // GREATER_EQUAL
        {
            mGREATER_EQUAL();

        }
            break;
        case 61:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:543:
        // NOT_EQUAL
        {
            mNOT_EQUAL();

        }
            break;
        case 62:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:553:
        // AND
        {
            mAND();

        }
            break;
        case 63:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:557:
        // OR
        {
            mOR();

        }
            break;
        case 64:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:560:
        // OPEN_BRACE
        {
            mOPEN_BRACE();

        }
            break;
        case 65:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:571:
        // CLOSE_BRACE
        {
            mCLOSE_BRACE();

        }
            break;
        case 66:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:583:
        // OPEN_CURLY_BRACE
        {
            mOPEN_CURLY_BRACE();

        }
            break;
        case 67:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:600:
        // CLOSE_CURLY_BRACE
        {
            mCLOSE_CURLY_BRACE();

        }
            break;
        case 68:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:618:
        // OPEN_SQUARE_BRACE
        {
            mOPEN_SQUARE_BRACE();

        }
            break;
        case 69:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:636:
        // CLOSE_SQUARE_BRACE
        {
            mCLOSE_SQUARE_BRACE();

        }
            break;
        case 70:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:655:
        // SEMICOLON
        {
            mSEMICOLON();

        }
            break;
        case 71:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:665:
        // DOT
        {
            mDOT();

        }
            break;
        case 72:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:669:
        // PLUS
        {
            mPLUS();

        }
            break;
        case 73:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:674:
        // MINUS
        {
            mMINUS();

        }
            break;
        case 74:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:680:
        // ASTERISK
        {
            mASTERISK();

        }
            break;
        case 75:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:689:
        // COMMA
        {
            mCOMMA();

        }
            break;
        case 76:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:695:
        // NOT
        {
            mNOT();

        }
            break;
        case 77:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:699:
        // DIVIDE
        {
            mDIVIDE();

        }
            break;
        case 78:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:706:
        // EQUAL
        {
            mEQUAL();

        }
            break;
        case 79:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:712:
        // LESS
        {
            mLESS();

        }
            break;
        case 80:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:717:
        // GREATER
        {
            mGREATER();

        }
            break;
        case 81:
        // /home/yasima/Acacia/x10dt/workspace/Acacia/src/org/acacia/rdf/sparql/Sparql.g:1:725:
        // ANY
        {
            mANY();

        }
            break;

        }
    }

    protected DFA16 dfa16 = new DFA16(this);
    protected DFA33 dfa33 = new DFA33(this);
    static final String DFA16_eotS = "\5\uffff";
    static final String DFA16_eofS = "\5\uffff";
    static final String DFA16_minS = "\2\56\3\uffff";
    static final String DFA16_maxS = "\1\71\1\145\3\uffff";
    static final String DFA16_acceptS = "\2\uffff\1\2\1\1\1\3";
    static final String DFA16_specialS = "\5\uffff}>";
    static final String[] DFA16_transitionS = { "\1\2\1\uffff\12\1",
            "\1\3\1\uffff\12\1\13\uffff\1\4\37\uffff\1\4", "", "", "" };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA
            .unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA
            .unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA
            .unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    protected class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }

        @Override
        public String getDescription() {
            return "559:1: DOUBLE : ( ( DIGIT )+ DOT ( DIGIT )* EXPONENT | DOT ( DIGIT )+ EXPONENT | ( DIGIT )+ EXPONENT );";
        }
    }

    static final String DFA33_eotS = "\2\uffff\1\61\1\71\5\61\1\105\13\61\1\125\4\61\1\133\1\137\1\141\1\144"
            + "\4\61\1\156\1\160\2\61\16\uffff\1\177\3\uffff\1\71\33\uffff\1\u009e\7"
            + "\uffff\1\133\1\u009f\2\uffff\1\u009f\1\uffff\1\u00a1\2\uffff\1\u00a5\1"
            + "\uffff\1\150\1\uffff\1\152\31\uffff\1\u00af\7\uffff\1\u00b7\1\u00b8\23"
            + "\uffff\1\u009f\1\uffff\1\u00ca\1\uffff\1\u00ca\1\uffff\1\u00cc\1\uffff"
            + "\1\u00cc\2\uffff\1\u00ce\6\uffff\1\u00d5\6\uffff\1\u00da\10\uffff\1\u00e4"
            + "\6\uffff\1\u00eb\1\uffff\1\u00ca\1\uffff\1\u00cc\1\uffff\1\u00ec\10\uffff"
            + "\1\u00f4\3\uffff\1\u00f7\1\u00f8\1\u00f9\1\u00fa\2\uffff\1\u00fd\2\uffff"
            + "\1\u00ff\1\u0100\1\u0101\1\u0102\4\uffff\1\u0105\1\u0106\7\uffff\1\u010d"
            + "\4\uffff\1\u010e\17\uffff\1\u0117\5\uffff\1\u011b\1\uffff\1\u011d\1\u011e"
            + "\1\u011f\1\u0120\2\uffff\1\u0122\7\uffff\1\u0125\2\uffff\1\u0127\3\uffff"
            + "\1\u0129\1\uffff";
    static final String DFA33_eofS = "\u012a\uffff";
    static final String DFA33_minS = "\1\0\1\uffff\1\55\1\60\21\55\1\41\1\72\2\60\1\101\1\56\1\60\2\56\3\0\1"
            + "\136\2\75\1\46\1\174\15\uffff\5\55\1\60\2\uffff\12\55\1\uffff\16\55\1"
            + "\41\7\uffff\1\56\1\60\2\uffff\1\60\1\uffff\1\56\1\60\1\uffff\1\56\1\60"
            + "\1\47\1\uffff\1\42\24\uffff\1\55\1\uffff\36\55\2\uffff\1\60\1\uffff\1"
            + "\60\1\uffff\1\60\1\uffff\1\60\1\uffff\1\60\2\uffff\4\55\1\uffff\7\55\2"
            + "\uffff\21\55\1\uffff\1\60\1\uffff\1\60\1\uffff\6\55\1\uffff\4\55\1\uffff"
            + "\11\55\1\uffff\6\55\2\uffff\7\55\1\uffff\2\55\4\uffff\2\55\1\uffff\1\55"
            + "\4\uffff\2\55\2\uffff\6\55\2\uffff\10\55\1\uffff\3\55\1\uffff\1\55\4\uffff"
            + "\1\55\1\uffff\2\55\1\uffff\1\55\1\uffff\1\55\1\uffff";
    static final String DFA33_maxS = "\1\uffff\1\uffff\23\ufffd\1\uffff\1\72\3\ufffd\1\145\3\71\3\uffff\1\136"
            + "\2\75\1\46\1\174\15\uffff\6\ufffd\2\uffff\12\ufffd\1\uffff\16\ufffd\1"
            + "\uffff\7\uffff\2\145\2\uffff\1\145\1\uffff\1\145\1\71\1\uffff\1\145\1"
            + "\71\1\47\1\uffff\1\42\24\uffff\1\ufffd\1\uffff\36\ufffd\2\uffff\1\145"
            + "\1\uffff\1\145\1\uffff\1\145\1\uffff\1\145\1\uffff\1\145\2\uffff\4\ufffd"
            + "\1\uffff\7\ufffd\2\uffff\21\ufffd\1\uffff\1\145\1\uffff\1\145\1\uffff"
            + "\6\ufffd\1\uffff\4\ufffd\1\uffff\11\ufffd\1\uffff\6\ufffd\2\uffff\7\ufffd"
            + "\1\uffff\2\ufffd\4\uffff\2\ufffd\1\uffff\1\ufffd\4\uffff\2\ufffd\2\uffff"
            + "\6\ufffd\2\uffff\10\ufffd\1\uffff\3\ufffd\1\uffff\1\ufffd\4\uffff\1\ufffd"
            + "\1\uffff\2\ufffd\1\uffff\1\ufffd\1\uffff\1\ufffd\1\uffff";
    static final String DFA33_acceptS = "\1\uffff\1\1\44\uffff\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\112"
            + "\1\113\1\115\1\116\1\121\1\1\6\uffff\1\2\1\3\12\uffff\1\31\17\uffff\1"
            + "\117\1\47\1\50\1\51\1\52\1\53\1\54\2\uffff\1\56\1\107\1\uffff\1\110\2"
            + "\uffff\1\111\3\uffff\1\65\1\uffff\1\66\1\71\1\72\1\74\1\120\1\75\1\114"
            + "\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\112\1\113\1\115"
            + "\1\116\1\uffff\1\20\36\uffff\1\73\1\55\1\uffff\1\57\1\uffff\1\61\1\uffff"
            + "\1\62\1\uffff\1\64\1\uffff\1\67\1\70\4\uffff\1\32\7\uffff\1\13\1\21\21"
            + "\uffff\1\60\1\uffff\1\63\1\uffff\1\4\6\uffff\1\22\4\uffff\1\14\11\uffff"
            + "\1\33\6\uffff\1\45\1\36\7\uffff\1\44\2\uffff\1\46\1\15\1\16\1\17\2\uffff"
            + "\1\23\1\uffff\1\26\1\27\1\40\1\41\2\uffff\1\5\1\6\6\uffff\1\30\1\24\10"
            + "\uffff\1\10\3\uffff\1\42\1\uffff\1\37\1\7\1\12\1\35\1\uffff\1\25\2\uffff"
            + "\1\11\1\uffff\1\43\1\uffff\1\34";
    static final String DFA33_specialS = "\1\1\35\uffff\1\2\1\0\1\3\u0109\uffff}>";
    static final String[] DFA33_transitionS = {
            "\11\61\2\1\2\61\1\1\22\61\1\1\1\43\1\37\1\40\1\30\1\61\1\44\1\36\1\46"
                    + "\1\47\1\55\1\34\1\56\1\35\1\33\1\57\12\32\1\3\1\54\1\25\1\60\1\42\1\27"
                    + "\1\31\1\22\1\2\1\10\1\6\1\24\1\12\1\17\1\24\1\21\2\24\1\16\1\24\1\13"
                    + "\1\15\1\4\1\24\1\7\1\5\1\23\1\20\1\24\1\14\3\24\1\52\1\61\1\53\1\41\1"
                    + "\26\1\61\1\11\1\2\1\10\1\6\1\24\1\12\1\17\1\24\1\21\2\24\1\16\1\24\1"
                    + "\13\1\15\1\4\1\24\1\7\1\5\1\23\1\20\1\24\1\14\3\24\1\50\1\45\1\51\102"
                    + "\61\27\24\1\61\37\24\1\61\u0208\24\160\61\16\24\1\61\u1c81\24\14\61\2"
                    + "\24\142\61\u0120\24\u0a70\61\u03f0\24\21\61\ua7ff\24\u2100\61\u04d0\24"
                    + "\40\61\u020e\24\2\61",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\63\15\66\1\65\11\66\1\64\1\66"
                    + "\4\uffff\1\66\1\uffff\1\63\15\66\1\65\11\66\1\64\1\66\74\uffff\1\66\10"
                    + "\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff"
                    + "\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff"
                    + "\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\12\72\7\uffff\32\72\4\uffff\1\72\1\uffff\32\72\105\uffff\27\72\1\uffff"
                    + "\37\72\1\uffff\u0208\72\160\uffff\16\72\1\uffff\u1c81\72\14\uffff\2\72"
                    + "\142\uffff\u0120\72\u0a70\uffff\u03f0\72\21\uffff\ua7ff\72\u2100\uffff"
                    + "\u04d0\72\40\uffff\u020e\72",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\73\10\66\4\uffff\1\66"
                    + "\1\uffff\21\66\1\73\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\76\3\66\1\74\16\66\1\75\6\66"
                    + "\4\uffff\1\66\1\uffff\1\76\3\66\1\74\16\66\1\75\6\66\74\uffff\1\66\10"
                    + "\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff"
                    + "\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff"
                    + "\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\101\3\66\1\100\3\66\1\77\21"
                    + "\66\4\uffff\1\66\1\uffff\1\101\3\66\1\100\3\66\1\77\21\66\74\uffff\1"
                    + "\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14"
                    + "\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff"
                    + "\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\102\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\102\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\16\66\1\103\13\66\4\uffff\1\66"
                    + "\1\uffff\16\66\1\103\13\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\22\66\1\104\7\66\4\uffff\1\66"
                    + "\1\uffff\22\66\1\104\7\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\110\7\66\1\107\10\66\1\106\10"
                    + "\66\4\uffff\1\66\1\uffff\1\110\7\66\1\107\10\66\1\106\10\66\74\uffff"
                    + "\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff\u1c81\66"
                    + "\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21"
                    + "\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\111\31\66\4\uffff\1\66\1\uffff"
                    + "\1\111\31\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286"
                    + "\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70"
                    + "\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e"
                    + "\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\7\66\1\112\22\66\4\uffff\1\66"
                    + "\1\uffff\7\66\1\112\22\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\5\66\1\114\11\66\1\115\1\66\1"
                    + "\113\10\66\4\uffff\1\66\1\uffff\5\66\1\114\11\66\1\115\1\66\1\113\10"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\117\7\66\1\116\21\66\4\uffff"
                    + "\1\66\1\uffff\1\117\7\66\1\116\21\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\120\10\66\4\uffff\1\66"
                    + "\1\uffff\21\66\1\120\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\15\66\1\121\14\66\4\uffff\1\66"
                    + "\1\uffff\15\66\1\121\14\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\22\66\1\122\7\66\4\uffff\1\66"
                    + "\1\uffff\22\66\1\122\7\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\22\66\1\104\7\66\4\uffff\1\66"
                    + "\1\uffff\22\66\1\104\7\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\123\10\66\4\uffff\1\66"
                    + "\1\uffff\21\66\1\123\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\126\1\uffff\31\126\1\uffff\1\124\36\126\1\uffff\1\126\1\uffff\1\126"
                    + "\1\uffff\32\126\3\uffff\uff82\126",
            "\1\127",
            "\12\130\7\uffff\32\130\4\uffff\1\130\1\uffff\32\130\105\uffff\27\130"
                    + "\1\uffff\37\130\1\uffff\u0208\130\160\uffff\16\130\1\uffff\u1c81\130"
                    + "\14\uffff\2\130\142\uffff\u0120\130\u0a70\uffff\u03f0\130\21\uffff\ua7ff"
                    + "\130\u2100\uffff\u04d0\130\40\uffff\u020e\130",
            "\12\131\7\uffff\32\131\4\uffff\1\131\1\uffff\32\131\105\uffff\27\131"
                    + "\1\uffff\37\131\1\uffff\u0208\131\160\uffff\16\131\1\uffff\u1c81\131"
                    + "\14\uffff\2\131\142\uffff\u0120\131\u0a70\uffff\u03f0\131\21\uffff\ua7ff"
                    + "\131\u2100\uffff\u04d0\131\40\uffff\u020e\131",
            "\32\132\6\uffff\32\132\105\uffff\27\132\1\uffff\37\132\1\uffff\u0208"
                    + "\132\160\uffff\16\132\1\uffff\u1c81\132\14\uffff\2\132\142\uffff\u0120"
                    + "\132\u0a70\uffff\u03f0\132\21\uffff\ua7ff\132\u2100\uffff\u04d0\132\40"
                    + "\uffff\u020e\132",
            "\1\135\1\uffff\12\134\13\uffff\1\136\37\uffff\1\136",
            "\12\140",
            "\1\143\1\uffff\12\142",
            "\1\146\1\uffff\12\145",
            "\12\150\1\uffff\2\150\1\uffff\31\150\1\147\uffd8\150",
            "\12\152\1\uffff\2\152\1\uffff\24\152\1\151\uffdd\152",
            "\0\153",
            "\1\154",
            "\1\155",
            "\1\157",
            "\1\161",
            "\1\162",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\22\66\1\176\7\66\4\uffff\1\66"
                    + "\1\uffff\22\66\1\176\7\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\24\66\1\u0080\5\66\4\uffff\1\66"
                    + "\1\uffff\24\66\1\u0080\5\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\7\uffff\32\66\4\uffff\1\66\1\uffff\32\66\74"
                    + "\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff\u1c81"
                    + "\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0\66"
                    + "\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\12\72\7\uffff\32\72\4\uffff\1\72\1\uffff\32\72\105\uffff\27\72\1\uffff"
                    + "\37\72\1\uffff\u0208\72\160\uffff\16\72\1\uffff\u1c81\72\14\uffff\2\72"
                    + "\142\uffff\u0120\72\u0a70\uffff\u03f0\72\21\uffff\ua7ff\72\u2100\uffff"
                    + "\u04d0\72\40\uffff\u020e\72",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u0081\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u0081\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\13\66\1\u0082\16\66\4\uffff\1"
                    + "\66\1\uffff\13\66\1\u0082\16\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\u0083\10\66\4\uffff\1"
                    + "\66\1\uffff\21\66\1\u0083\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\14\66\1\u0084\15\66\4\uffff\1"
                    + "\66\1\uffff\14\66\1\u0084\15\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\22\66\1\u0085\7\66\4\uffff\1\66"
                    + "\1\uffff\22\66\1\u0085\7\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\22\66\1\u0086\7\66\4\uffff\1\66"
                    + "\1\uffff\22\66\1\u0086\7\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u0087\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u0087\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\3\66\1\u0088\2\66\1\u0089\23\66"
                    + "\4\uffff\1\66\1\uffff\3\66\1\u0088\2\66\1\u0089\23\66\74\uffff\1\66\10"
                    + "\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff"
                    + "\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff"
                    + "\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\15\66\1\u008a\14\66\4\uffff\1"
                    + "\66\1\uffff\15\66\1\u008a\14\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\2\66\1\u008c\7\66\1\u008b\17\66"
                    + "\4\uffff\1\66\1\uffff\2\66\1\u008c\7\66\1\u008b\17\66\74\uffff\1\66\10"
                    + "\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff"
                    + "\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff"
                    + "\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\16\66\1\u008d\13\66\4\uffff\1"
                    + "\66\1\uffff\16\66\1\u008d\13\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\13\66\1\u008e\16\66\4\uffff\1"
                    + "\66\1\uffff\13\66\1\u008e\16\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\13\66\1\u008f\16\66\4\uffff\1"
                    + "\66\1\uffff\13\66\1\u008f\16\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\14\66\1\u0090\15\66\4\uffff\1"
                    + "\66\1\uffff\14\66\1\u0090\15\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u0091\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u0091\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\3\66\1\u0092\26\66\4\uffff\1\66"
                    + "\1\uffff\3\66\1\u0092\26\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\5\66\1\u0093\24\66\4\uffff\1\66"
                    + "\1\uffff\5\66\1\u0093\24\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u0094\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u0094\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\14\66\1\u0095\15\66\4\uffff\1"
                    + "\66\1\uffff\14\66\1\u0095\15\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\15\66\1\u0096\14\66\4\uffff\1"
                    + "\66\1\uffff\15\66\1\u0096\14\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\u0097\31\66\4\uffff\1\66\1\uffff"
                    + "\1\u0097\31\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286"
                    + "\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70"
                    + "\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e"
                    + "\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\10\66\1\u0098\21\66\4\uffff\1"
                    + "\66\1\uffff\10\66\1\u0098\21\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\66\1\u009b\6\66\1\u0099\2\66"
                    + "\1\u009c\10\66\1\u009a\5\66\4\uffff\1\66\1\uffff\1\66\1\u009b\6\66\1"
                    + "\u0099\2\66\1\u009c\10\66\1\u009a\5\66\74\uffff\1\66\10\uffff\27\66\1"
                    + "\uffff\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff"
                    + "\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100"
                    + "\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\24\66\1\u009d\5\66\4\uffff\1\66"
                    + "\1\uffff\24\66\1\u009d\5\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\126\1\uffff\31\126\1\uffff\37\126\1\uffff\1\126\1\uffff\1\126\1\uffff"
                    + "\32\126\3\uffff\uff82\126",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\135\1\uffff\12\134\13\uffff\1\136\37\uffff\1\136",
            "\12\u00a0\13\uffff\1\136\37\uffff\1\136",
            "",
            "",
            "\12\140\13\uffff\1\136\37\uffff\1\136",
            "",
            "\1\u00a2\1\uffff\12\142\13\uffff\1\u00a3\37\uffff\1\u00a3",
            "\12\u00a4",
            "",
            "\1\u00a6\1\uffff\12\145\13\uffff\1\u00a7\37\uffff\1\u00a7",
            "\12\u00a8",
            "\1\u00a9",
            "",
            "\1\u00aa",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00ab\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00ab\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\15\66\1\u00ac\14\66\4\uffff\1"
                    + "\66\1\uffff\15\66\1\u00ac\14\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\5\66\1\u00ad\24\66\4\uffff\1\66"
                    + "\1\uffff\5\66\1\u00ad\24\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00ae\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00ae\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00b0\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00b0\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u00b1\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u00b1\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\2\66\1\u00b2\27\66\4\uffff\1\66"
                    + "\1\uffff\2\66\1\u00b2\27\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\u00b3\31\66\4\uffff\1\66\1\uffff"
                    + "\1\u00b3\31\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286"
                    + "\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70"
                    + "\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e"
                    + "\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\24\66\1\u00b4\5\66\4\uffff\1\66"
                    + "\1\uffff\24\66\1\u00b4\5\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00b5\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00b5\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\22\66\1\u00b6\7\66\4\uffff\1\66"
                    + "\1\uffff\22\66\1\u00b6\7\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\14\66\1\u00b9\15\66\4\uffff\1"
                    + "\66\1\uffff\14\66\1\u00b9\15\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u00ba\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u00ba\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\22\66\1\u00bb\7\66\4\uffff\1\66"
                    + "\1\uffff\22\66\1\u00bb\7\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00bc\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00bc\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\u00bd\10\66\4\uffff\1"
                    + "\66\1\uffff\21\66\1\u00bd\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00be\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00be\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\22\66\1\u00bf\7\66\4\uffff\1\66"
                    + "\1\uffff\22\66\1\u00bf\7\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\10\66\1\u00c0\21\66\4\uffff\1"
                    + "\66\1\uffff\10\66\1\u00c0\21\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\10\66\1\u00c1\21\66\4\uffff\1"
                    + "\66\1\uffff\10\66\1\u00c1\21\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\6\66\1\u00c2\23\66\4\uffff\1\66"
                    + "\1\uffff\6\66\1\u00c2\23\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\17\66\1\u00c3\12\66\4\uffff\1"
                    + "\66\1\uffff\17\66\1\u00c3\12\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\16\66\1\u00c4\13\66\4\uffff\1"
                    + "\66\1\uffff\16\66\1\u00c4\13\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\u00c5\10\66\4\uffff\1"
                    + "\66\1\uffff\21\66\1\u00c5\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\u00c6\10\66\4\uffff\1"
                    + "\66\1\uffff\21\66\1\u00c6\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\13\66\1\u00c7\16\66\4\uffff\1"
                    + "\66\1\uffff\13\66\1\u00c7\16\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\10\66\1\u00c8\21\66\4\uffff\1"
                    + "\66\1\uffff\10\66\1\u00c8\21\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00c9\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00c9\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "",
            "",
            "\12\u00a0\13\uffff\1\136\37\uffff\1\136",
            "",
            "\12\u00cb\13\uffff\1\u00a3\37\uffff\1\u00a3",
            "",
            "\12\u00a4\13\uffff\1\u00a3\37\uffff\1\u00a3",
            "",
            "\12\u00cd\13\uffff\1\u00a7\37\uffff\1\u00a7",
            "",
            "\12\u00a8\13\uffff\1\u00a7\37\uffff\1\u00a7",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\3\66\1\u00cf\26\66\4\uffff\1\66"
                    + "\1\uffff\3\66\1\u00cf\26\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\10\66\1\u00d0\21\66\4\uffff\1"
                    + "\66\1\uffff\10\66\1\u00d0\21\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\2\66\1\u00d1\27\66\4\uffff\1\66"
                    + "\1\uffff\2\66\1\u00d1\27\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u00d2\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u00d2\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\10\66\1\u00d3\21\66\4\uffff\1"
                    + "\66\1\uffff\10\66\1\u00d3\21\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\u00d4\10\66\4\uffff\1"
                    + "\66\1\uffff\21\66\1\u00d4\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u00d6\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u00d6\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\2\66\1\u00d7\27\66\4\uffff\1\66"
                    + "\1\uffff\2\66\1\u00d7\27\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\27\66\1\u00d8\2\66\4\uffff\1\66"
                    + "\1\uffff\27\66\1\u00d8\2\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u00d9\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u00d9\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00db\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00db\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00dc\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00dc\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\3\66\1\u00dd\26\66\4\uffff\1\66"
                    + "\1\uffff\3\66\1\u00dd\26\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00de\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00de\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\u00df\10\66\4\uffff\1"
                    + "\66\1\uffff\21\66\1\u00df\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00e0\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00e0\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\16\66\1\u00e1\13\66\4\uffff\1"
                    + "\66\1\uffff\16\66\1\u00e1\13\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u00e2\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u00e2\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\14\66\1\u00e3\15\66\4\uffff\1"
                    + "\66\1\uffff\14\66\1\u00e3\15\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\7\66\1\u00e5\22\66\4\uffff\1\66"
                    + "\1\uffff\7\66\1\u00e5\22\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\15\66\1\u00e6\14\66\4\uffff\1"
                    + "\66\1\uffff\15\66\1\u00e6\14\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\10\66\1\u00e7\21\66\4\uffff\1"
                    + "\66\1\uffff\10\66\1\u00e7\21\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\10\66\1\u00e8\21\66\4\uffff\1"
                    + "\66\1\uffff\10\66\1\u00e8\21\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\u00e9\31\66\4\uffff\1\66\1\uffff"
                    + "\1\u00e9\31\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286"
                    + "\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70"
                    + "\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e"
                    + "\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u00ea\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u00ea\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "",
            "\12\u00cb\13\uffff\1\u00a3\37\uffff\1\u00a3",
            "",
            "\12\u00cd\13\uffff\1\u00a7\37\uffff\1\u00a7",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\27\66\1\u00ed\2\66\4\uffff\1\66"
                    + "\1\uffff\27\66\1\u00ed\2\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u00ee\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u00ee\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00ef\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00ef\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\15\66\1\u00f0\14\66\4\uffff\1"
                    + "\66\1\uffff\15\66\1\u00f0\14\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\10\66\1\u00f1\21\66\4\uffff\1"
                    + "\66\1\uffff\10\66\1\u00f1\21\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\30\66\1\u00f2\1\66\4\uffff\1\66"
                    + "\1\uffff\30\66\1\u00f2\1\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u00f3\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u00f3\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\u00f5\10\66\4\uffff\1"
                    + "\66\1\uffff\21\66\1\u00f5\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\u00f6\10\66\4\uffff\1"
                    + "\66\1\uffff\21\66\1\u00f6\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u00fb\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u00fb\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\15\66\1\u00fc\14\66\4\uffff\1"
                    + "\66\1\uffff\15\66\1\u00fc\14\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\u00fe\31\66\4\uffff\1\66\1\uffff"
                    + "\1\u00fe\31\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286"
                    + "\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70"
                    + "\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e"
                    + "\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\15\66\1\u0103\14\66\4\uffff\1"
                    + "\66\1\uffff\15\66\1\u0103\14\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u0104\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u0104\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\u0107\10\66\4\uffff\1"
                    + "\66\1\uffff\21\66\1\u0107\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\2\66\1\u0108\27\66\4\uffff\1\66"
                    + "\1\uffff\2\66\1\u0108\27\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\66\1\u0109\30\66\4\uffff\1\66"
                    + "\1\uffff\1\66\1\u0109\30\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\17\66\1\u010a\12\66\4\uffff\1"
                    + "\66\1\uffff\17\66\1\u010a\12\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\3\66\1\u010b\26\66\4\uffff\1\66"
                    + "\1\uffff\3\66\1\u010b\26\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\24\66\1\u010c\5\66\4\uffff\1\66"
                    + "\1\uffff\24\66\1\u010c\5\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "",
            "",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\u010f\31\66\4\uffff\1\66\1\uffff"
                    + "\1\u010f\31\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286"
                    + "\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70"
                    + "\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e"
                    + "\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u0110\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u0110\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "",
            "",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\12\66\1\u0111\17\66\4\uffff\1"
                    + "\66\1\uffff\12\66\1\u0111\17\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\21\66\1\u0112\10\66\4\uffff\1"
                    + "\66\1\uffff\21\66\1\u0112\10\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\14\66\1\u0113\15\66\4\uffff\1"
                    + "\66\1\uffff\14\66\1\u0113\15\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u0114\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u0114\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u0115\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u0115\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u0116\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u0116\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\2\66\1\u0118\27\66\4\uffff\1\66"
                    + "\1\uffff\2\66\1\u0118\27\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\13\66\1\u0119\16\66\4\uffff\1"
                    + "\66\1\uffff\13\66\1\u0119\16\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\2\66\1\u011a\27\66\4\uffff\1\66"
                    + "\1\uffff\2\66\1\u011a\27\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\1\u011c\31\66\4\uffff\1\66\1\uffff"
                    + "\1\u011c\31\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286"
                    + "\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70"
                    + "\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e"
                    + "\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\23\66\1\u0121\6\66\4\uffff\1\66"
                    + "\1\uffff\23\66\1\u0121\6\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\7\66\1\u0123\22\66\4\uffff\1\66"
                    + "\1\uffff\7\66\1\u0123\22\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\13\66\1\u0124\16\66\4\uffff\1"
                    + "\66\1\uffff\13\66\1\u0124\16\66\74\uffff\1\66\10\uffff\27\66\1\uffff"
                    + "\37\66\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66"
                    + "\57\uffff\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff"
                    + "\u04d0\66\40\uffff\u020e\66",
            "",
            "",
            "",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\4\66\1\u0126\25\66\4\uffff\1\66"
                    + "\1\uffff\4\66\1\u0126\25\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\22\66\1\u0128\7\66\4\uffff\1\66"
                    + "\1\uffff\22\66\1\u0128\7\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66"
                    + "\1\uffff\u0286\66\1\uffff\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff"
                    + "\u0120\66\u0a70\uffff\u03f0\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66"
                    + "\40\uffff\u020e\66",
            "",
            "\1\66\1\67\1\uffff\12\66\1\70\6\uffff\32\66\4\uffff\1\66\1\uffff\32"
                    + "\66\74\uffff\1\66\10\uffff\27\66\1\uffff\37\66\1\uffff\u0286\66\1\uffff"
                    + "\u1c81\66\14\uffff\2\66\61\uffff\2\66\57\uffff\u0120\66\u0a70\uffff\u03f0"
                    + "\66\21\uffff\ua7ff\66\u2100\uffff\u04d0\66\40\uffff\u020e\66",
            "" };

    static final short[] DFA33_eot = DFA.unpackEncodedString(DFA33_eotS);
    static final short[] DFA33_eof = DFA.unpackEncodedString(DFA33_eofS);
    static final char[] DFA33_min = DFA
            .unpackEncodedStringToUnsignedChars(DFA33_minS);
    static final char[] DFA33_max = DFA
            .unpackEncodedStringToUnsignedChars(DFA33_maxS);
    static final short[] DFA33_accept = DFA.unpackEncodedString(DFA33_acceptS);
    static final short[] DFA33_special = DFA
            .unpackEncodedString(DFA33_specialS);
    static final short[][] DFA33_transition;

    static {
        int numStates = DFA33_transitionS.length;
        DFA33_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++) {
            DFA33_transition[i] = DFA.unpackEncodedString(DFA33_transitionS[i]);
        }
    }

    protected class DFA33 extends DFA {

        public DFA33(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 33;
            this.eot = DFA33_eot;
            this.eof = DFA33_eof;
            this.min = DFA33_min;
            this.max = DFA33_max;
            this.accept = DFA33_accept;
            this.special = DFA33_special;
            this.transition = DFA33_transition;
        }

        @Override
        public String getDescription() {
            return "1:1: Tokens : ( WS | PNAME_NS | PNAME_LN | BASE | PREFIX | SELECT | DISTINCT | REDUCED | CONSTRUCT | DESCRIBE | ASK | FROM | NAMED | WHERE | ORDER | BY | ASC | DESC | LIMIT | OFFSET | OPTIONAL | GRAPH | UNION | FILTER | A | STR | LANG | LANGMATCHES | DATATYPE | BOUND | SAMETERM | ISIRI | ISURI | ISBLANK | ISLITERAL | REGEX | TRUE | FALSE | IRI_REF | BLANK_NODE_LABEL | VAR1 | VAR2 | LANGTAG | INTEGER | DECIMAL | DOUBLE | INTEGER_POSITIVE | DECIMAL_POSITIVE | DOUBLE_POSITIVE | INTEGER_NEGATIVE | DECIMAL_NEGATIVE | DOUBLE_NEGATIVE | STRING_LITERAL1 | STRING_LITERAL2 | STRING_LITERAL_LONG1 | STRING_LITERAL_LONG2 | COMMENT | REFERENCE | LESS_EQUAL | GREATER_EQUAL | NOT_EQUAL | AND | OR | OPEN_BRACE | CLOSE_BRACE | OPEN_CURLY_BRACE | CLOSE_CURLY_BRACE | OPEN_SQUARE_BRACE | CLOSE_SQUARE_BRACE | SEMICOLON | DOT | PLUS | MINUS | ASTERISK | COMMA | NOT | DIVIDE | EQUAL | LESS | GREATER | ANY );";
        }

        @Override
        public int specialStateTransition(int s, IntStream _input)
                throws NoViableAltException {
            IntStream input = _input;
            int _s = s;
            switch (s) {
            case 0:
                int LA33_31 = input.LA(1);
                s = -1;
                if ((LA33_31 == '\"')) {
                    s = 105;
                } else if (((LA33_31 >= '\u0000' && LA33_31 <= '\t')
                        || (LA33_31 >= '\u000B' && LA33_31 <= '\f')
                        || (LA33_31 >= '\u000E' && LA33_31 <= '!') || (LA33_31 >= '#' && LA33_31 <= '\uFFFF'))) {
                    s = 106;
                } else
                    s = 49;
                if (s >= 0)
                    return s;
                break;

            case 1:
                int LA33_0 = input.LA(1);
                s = -1;
                if (((LA33_0 >= '\t' && LA33_0 <= '\n') || LA33_0 == '\r' || LA33_0 == ' ')) {
                    s = 1;
                } else if ((LA33_0 == 'B' || LA33_0 == 'b')) {
                    s = 2;
                } else if ((LA33_0 == ':')) {
                    s = 3;
                } else if ((LA33_0 == 'P' || LA33_0 == 'p')) {
                    s = 4;
                } else if ((LA33_0 == 'S' || LA33_0 == 's')) {
                    s = 5;
                } else if ((LA33_0 == 'D' || LA33_0 == 'd')) {
                    s = 6;
                } else if ((LA33_0 == 'R' || LA33_0 == 'r')) {
                    s = 7;
                } else if ((LA33_0 == 'C' || LA33_0 == 'c')) {
                    s = 8;
                } else if ((LA33_0 == 'a')) {
                    s = 9;
                } else if ((LA33_0 == 'F' || LA33_0 == 'f')) {
                    s = 10;
                } else if ((LA33_0 == 'N' || LA33_0 == 'n')) {
                    s = 11;
                } else if ((LA33_0 == 'W' || LA33_0 == 'w')) {
                    s = 12;
                } else if ((LA33_0 == 'O' || LA33_0 == 'o')) {
                    s = 13;
                } else if ((LA33_0 == 'L' || LA33_0 == 'l')) {
                    s = 14;
                } else if ((LA33_0 == 'G' || LA33_0 == 'g')) {
                    s = 15;
                } else if ((LA33_0 == 'U' || LA33_0 == 'u')) {
                    s = 16;
                } else if ((LA33_0 == 'I' || LA33_0 == 'i')) {
                    s = 17;
                } else if ((LA33_0 == 'A')) {
                    s = 18;
                } else if ((LA33_0 == 'T' || LA33_0 == 't')) {
                    s = 19;
                } else if ((LA33_0 == 'E' || LA33_0 == 'H'
                        || (LA33_0 >= 'J' && LA33_0 <= 'K') || LA33_0 == 'M'
                        || LA33_0 == 'Q' || LA33_0 == 'V'
                        || (LA33_0 >= 'X' && LA33_0 <= 'Z') || LA33_0 == 'e'
                        || LA33_0 == 'h' || (LA33_0 >= 'j' && LA33_0 <= 'k')
                        || LA33_0 == 'm' || LA33_0 == 'q' || LA33_0 == 'v'
                        || (LA33_0 >= 'x' && LA33_0 <= 'z')
                        || (LA33_0 >= '\u00C0' && LA33_0 <= '\u00D6')
                        || (LA33_0 >= '\u00D8' && LA33_0 <= '\u00F6')
                        || (LA33_0 >= '\u00F8' && LA33_0 <= '\u02FF')
                        || (LA33_0 >= '\u0370' && LA33_0 <= '\u037D')
                        || (LA33_0 >= '\u037F' && LA33_0 <= '\u1FFF')
                        || (LA33_0 >= '\u200C' && LA33_0 <= '\u200D')
                        || (LA33_0 >= '\u2070' && LA33_0 <= '\u218F')
                        || (LA33_0 >= '\u2C00' && LA33_0 <= '\u2FEF')
                        || (LA33_0 >= '\u3001' && LA33_0 <= '\uD7FF')
                        || (LA33_0 >= '\uF900' && LA33_0 <= '\uFDCF') || (LA33_0 >= '\uFDF0' && LA33_0 <= '\uFFFD'))) {
                    s = 20;
                } else if ((LA33_0 == '<')) {
                    s = 21;
                } else if ((LA33_0 == '_')) {
                    s = 22;
                } else if ((LA33_0 == '?')) {
                    s = 23;
                } else if ((LA33_0 == '$')) {
                    s = 24;
                } else if ((LA33_0 == '@')) {
                    s = 25;
                } else if (((LA33_0 >= '0' && LA33_0 <= '9'))) {
                    s = 26;
                } else if ((LA33_0 == '.')) {
                    s = 27;
                } else if ((LA33_0 == '+')) {
                    s = 28;
                } else if ((LA33_0 == '-')) {
                    s = 29;
                } else if ((LA33_0 == '\'')) {
                    s = 30;
                } else if ((LA33_0 == '\"')) {
                    s = 31;
                } else if ((LA33_0 == '#')) {
                    s = 32;
                } else if ((LA33_0 == '^')) {
                    s = 33;
                } else if ((LA33_0 == '>')) {
                    s = 34;
                } else if ((LA33_0 == '!')) {
                    s = 35;
                } else if ((LA33_0 == '&')) {
                    s = 36;
                } else if ((LA33_0 == '|')) {
                    s = 37;
                } else if ((LA33_0 == '(')) {
                    s = 38;
                } else if ((LA33_0 == ')')) {
                    s = 39;
                } else if ((LA33_0 == '{')) {
                    s = 40;
                } else if ((LA33_0 == '}')) {
                    s = 41;
                } else if ((LA33_0 == '[')) {
                    s = 42;
                } else if ((LA33_0 == ']')) {
                    s = 43;
                } else if ((LA33_0 == ';')) {
                    s = 44;
                } else if ((LA33_0 == '*')) {
                    s = 45;
                } else if ((LA33_0 == ',')) {
                    s = 46;
                } else if ((LA33_0 == '/')) {
                    s = 47;
                } else if ((LA33_0 == '=')) {
                    s = 48;
                } else if (((LA33_0 >= '\u0000' && LA33_0 <= '\b')
                        || (LA33_0 >= '\u000B' && LA33_0 <= '\f')
                        || (LA33_0 >= '\u000E' && LA33_0 <= '\u001F')
                        || LA33_0 == '%' || LA33_0 == '\\' || LA33_0 == '`'
                        || (LA33_0 >= '~' && LA33_0 <= '\u00BF')
                        || LA33_0 == '\u00D7' || LA33_0 == '\u00F7'
                        || (LA33_0 >= '\u0300' && LA33_0 <= '\u036F')
                        || LA33_0 == '\u037E'
                        || (LA33_0 >= '\u2000' && LA33_0 <= '\u200B')
                        || (LA33_0 >= '\u200E' && LA33_0 <= '\u206F')
                        || (LA33_0 >= '\u2190' && LA33_0 <= '\u2BFF')
                        || (LA33_0 >= '\u2FF0' && LA33_0 <= '\u3000')
                        || (LA33_0 >= '\uD800' && LA33_0 <= '\uF8FF')
                        || (LA33_0 >= '\uFDD0' && LA33_0 <= '\uFDEF') || (LA33_0 >= '\uFFFE' && LA33_0 <= '\uFFFF'))) {
                    s = 49;
                }
                if (s >= 0)
                    return s;
                break;

            case 2:
                int LA33_30 = input.LA(1);
                s = -1;
                if ((LA33_30 == '\'')) {
                    s = 103;
                } else if (((LA33_30 >= '\u0000' && LA33_30 <= '\t')
                        || (LA33_30 >= '\u000B' && LA33_30 <= '\f')
                        || (LA33_30 >= '\u000E' && LA33_30 <= '&') || (LA33_30 >= '(' && LA33_30 <= '\uFFFF'))) {
                    s = 104;
                } else
                    s = 49;
                if (s >= 0)
                    return s;
                break;

            case 3:
                int LA33_32 = input.LA(1);
                s = -1;
                if (((LA33_32 >= '\u0000' && LA33_32 <= '\uFFFF'))) {
                    s = 107;
                } else
                    s = 49;
                if (s >= 0)
                    return s;
                break;
            }
            NoViableAltException nvae = new NoViableAltException(
                    getDescription(), 33, _s, input);
            error(nvae);
            throw nvae;
        }
    }

}
