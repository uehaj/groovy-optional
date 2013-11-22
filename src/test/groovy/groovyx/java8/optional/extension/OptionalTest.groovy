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

    def mult(a, b) {
        a * 3 + b * 2
    }

    void testMult() {
        assert mult(arg1, arg2) == Optional.of("AbcAbcAbcDefDef")
        assert mult(NULL, arg2) == NULL
        assert mult(arg1, NULL) == NULL
    }

    void testJoin() {
        assert (Optional.of{a,b -> String.join(' ', a, b) }(arg1, arg2)) == Optional.of("Abc Def")
        assert (Optional.of{a,b -> String.join(' ', a, b) }(NULL, arg2)) == NULL
        assert (Optional.of{a,b -> String.join(' ', a, b) }(arg1, NULL)) == NULL
        assert (Optional.of{a,b -> String.join(' ', a, b) }(NULL, NULL)) == NULL
    }
}
