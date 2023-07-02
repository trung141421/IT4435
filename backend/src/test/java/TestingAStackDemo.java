import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EmptyStackException;
import java.util.Stack;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.jupiter.api.*;
import trung.util.log.DebugLogger;

@DisplayName("A stack")
class TestingAStackDemo {
    Stack<Object> stack;
    @BeforeAll
    @DisplayName("initiate environment")
    static void initEnvironment(){
        DOMConfigurator.configure("config/log4j.xml");
        DebugLogger.info("initEnvironment");
    }
    @AfterAll
    @DisplayName("initiate environment")
    static void completeTest(){
        DebugLogger.info("completeTest");
    }

    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew() {
        new Stack<>();
        DebugLogger.info("isInstantiatedWithNew");
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createNewStack() {
            stack = new Stack<>();
            DebugLogger.info("createNewStack");
        }

        @Test
        @DisplayName("is empty")
        void isEmpty() {
            assertTrue(stack.isEmpty());
            DebugLogger.info("isEmpty");
        }

        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            assertThrows(EmptyStackException.class, stack::pop);
            DebugLogger.info("throwsExceptionWhenPopped");
        }

        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            assertThrows(EmptyStackException.class, stack::peek);
            DebugLogger.info("throwsExceptionWhenPeeked");
        }

        @Nested
        @DisplayName("after pushing an element")
        class AfterPushing {

            String anElement = "an element";

            @BeforeEach
            void pushAnElement() {
                stack.push(anElement);
                DebugLogger.info("pushAnElement");
            }

            @Test
            @DisplayName("it is no longer empty")
            void isNotEmpty() {
                assertFalse(stack.isEmpty());
                DebugLogger.info("isNotEmpty");
            }

            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped() {
                assertEquals(anElement, stack.pop());
                assertTrue(stack.isEmpty());
                DebugLogger.info("returnElementWhenPopped");
            }

            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked() {
                assertEquals(anElement, stack.peek());
                assertFalse(stack.isEmpty());
                DebugLogger.info("returnElementWhenPeeked");
            }
        }
    }
}