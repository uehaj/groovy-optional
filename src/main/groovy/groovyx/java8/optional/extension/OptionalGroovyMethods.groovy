/*
 * Copyright 2003-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package groovyx.java8.optional.extension;
import groovy.transform.CompileStatic;
import org.codehaus.groovy.runtime.InvokerHelper;

/**
 * @author Uehara Junji(@uehaj)
 */
class OptionalGroovyMethods {
    public static void hello(String s) {
        println "hello"
    }

    public static Object methodMissing(Optional self, String name, Object args0) {
        Object[] args = (Object[])args0;
        for (int i=0; i<args.size(); i++) {
            if (args[i] instanceof Optional) {
                if (args[i].isPresent()) {
                    args[i] = args[i].get();
                }
                else {
                    return Optional.empty();
                }
            }
        }
        if (self.isPresent()) {
            return Optional.ofNullable(self.get().invokeMethod(name, args));
        }
        else {
            return Optional.empty();
        }
    }


}

