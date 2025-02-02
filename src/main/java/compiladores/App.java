package compiladores;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

// Las diferentes entradas se explicaran oportunamente
public class App {
    public static void main(String[] args) throws Exception {
        // create a CharStream that reads from file
        CharStream input = CharStreams.fromFileName("input/codigo_tres.c");

        // create a lexer that feeds off of input CharStream
        compiladorLexer lexer = new compiladorLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        compiladorParser parser = new compiladorParser(tokens);
        // create Listener
        compiladorBaseListener escucha = new Escucha();

        // Conecto el objeto con Listeners al parser
        parser.addParseListener(escucha);

        // Solicito al parser que comience indicando una regla gramatical
        // En este caso la regla es el simbolo inicial

        ParseTree tree = parser.programa();

        boolean err = (((Escucha) escucha).errors);

        if (err || parser.getNumberOfSyntaxErrors() > 0) {
            System.out.println("Algun error... termino");
            return;
        }
        System.out.println("Ningun error, continuo..");

        // Conectamos el visitor
        Caminante visitor = new Caminante();
        visitor.visit(tree);

        OptimizadorTAC optimizador = new OptimizadorTAC("codigo_tres.txt");
        // optimizador.optimiza();
        // System.out.println(visitor);
        // System.out.println(visitor.getErrorNodes());
        // Imprime el arbol obtenido
        // System.out.println(tree.toStringTree(parser));
        // System.out.println(escucha);

    }
}
