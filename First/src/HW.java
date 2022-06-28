import java.awt.Color;

import javax.swing.JFrame;

public class HW extends JFrame {

	public HW(){
	    super("My First Window"); //Заголовок окна
	    setBounds(100, 100, 200, 200); //Если не выставить 
	                                   //размер и положение 
	                                   //то окно будет мелкое и незаметное
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //это нужно для того чтобы при 
	                                                    //закрытии окна закрывалась и программа,
	                                                    //иначе она останется висеть в процессах
	    getContentPane().setBackground(Color.black);
	  }

	  public static void main(String[] args) { //эта функция может быть и в другом классе
		HW app = new HW(); //Создаем экземпляр нашего приложения
	    app.setVisible(true); //С этого момента приложение запущено!
	  }

}
