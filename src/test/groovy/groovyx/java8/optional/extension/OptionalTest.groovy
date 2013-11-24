package groovyx.java8.optional.extension;
import java.util.Optional;

class OptionalTest extends GroovyTestCase {
    Optional<String> arg1 = Optional.of("Abc")
    Optional<String> arg2 = Optional.of("Def")
    Optional<String> NULL = Optional.empty()
    
    def add(a,b) {
        a+b
    }

    void testAdd() {
        assert add(arg1, arg2) == Optional.of("AbcDef") // Optional.equals() will be called()
        assert add(NULL, arg2) == NULL
        assert add(arg1, NULL) == NULL
        assert add(NULL, NULL) == NULL
    }

    def toUpperAndToLower(a,b) {
        a.toUpperCase()+b.toLowerCase()
    }

    void testToUpperAndToLower() {
        assert toUpperAndToLower(arg1, arg2) == Optional.of("ABCdef")
        assert toUpperAndToLower(arg1, NULL) == NULL
    }

    def mult1(a, b) {
        a * 3 + b * 2
    }

    void testMult1() {
        assert mult1(arg1, arg2) == Optional.of("AbcAbcAbcDefDef")
        assert mult1(NULL, arg2) == NULL
        assert mult1(arg1, NULL) == NULL

        assert mult1(Optional.of("Abc"), Optional.of(3)) == Optional.of("AbcAbcAbc6")
    }

    Optional<Integer> mult2(Optional<Integer>a, Optional<Integer>b) {
        a * 3 + b * 2
    }

    void testMult2() {
        assert mult2(arg1, arg2) == Optional.of("AbcAbcAbcDefDef")
        assert mult2(NULL, arg2) == NULL
        assert mult2(arg1, NULL) == NULL
    }

    void testJoin() {
        assert (Optional.of{a,b -> String.join(' ', a, b) }(arg1, arg2)) == Optional.of("Abc Def")
        assert (Optional.of{a,b -> String.join(' ', a, b) }(NULL, arg2)) == NULL
        assert (Optional.of{a,b -> String.join(' ', a, b) }(arg1, NULL)) == NULL
        assert (Optional.of{a,b -> String.join(' ', a, b) }(NULL, NULL)) == NULL
    }

    void testToString() {
        def a = Optional.of(3.3)
        assert a.toString() == "Optional[3.3]" // != "3.3"
        assert a.get().toString() == "3.3"
        assert a.map{"<"+it.toString()+">"}.get() == "<3.3>"
    }
}
