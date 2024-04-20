import interfaces.AutoInjectable;
import interfaces.SomeInterface;
import interfaces.SomeOtherInterface;

class SomeBean{
    @AutoInjectable
    private SomeInterface field1;
    @AutoInjectable
    private SomeOtherInterface field2;
    public void foo(){
        field1.doSomething();
        field2.doSomeOther();
    }
}

