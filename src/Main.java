//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SomeBean sb = new SomeBean();
        sb = new Injector("src/config.properties").inject(sb);
        sb.foo();
    }
}