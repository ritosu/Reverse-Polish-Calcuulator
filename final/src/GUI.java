import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application {
	private TextArea formulaField;
	private TextArea stack;
	private Button runButton;

	private void action() {
		String str = formulaField.getText();
		stack.setText(str);
	}
	public void start(Stage stage) {
		int buttonsize = 50;
		int centersizeH = 500;
		int centersizeW = 280;
		int numsbuttonsizeH = 300;
		int numsbuttonsizeW = 250;
		int stackfieldsizeW = 120;
		formulaField = new TextArea();
		formulaField.setPrefSize(centersizeW, centersizeH-numsbuttonsizeH-10);
		stack = new TextArea();
		stack.setPrefSize(stackfieldsizeW, centersizeH);
		stack.setEditable(false);
		runButton = new Button("Run");
		runButton.setOnAction(e -> action());
		
		Button[] numbutton = new Button[10];
		Button[] opebutton = new Button[10];//.,␣,＝,＋,−,×,÷,^,a,←
		opebutton[0] = new Button(".");
		opebutton[1] = new Button("␣");
		opebutton[2] = new Button("=");
		opebutton[3] = new Button("+");
		opebutton[4] = new Button("-");
		opebutton[5] = new Button("*");
		opebutton[6] = new Button("/");
		opebutton[7] = new Button("^");
		opebutton[8] = new Button("a");
		opebutton[9] = new Button("←");
		for(int i = 0; i < 10; ++i) {
			opebutton[i].setPrefSize(buttonsize, buttonsize);
			String buttonText = opebutton[i].getText();
			opebutton[i].setOnAction(event -> formulaField.appendText(buttonText));
		}
		opebutton[1].setOnAction(event -> formulaField.appendText(" "));//␣
		opebutton[2].setOnAction(event -> (new RPCCLI()).calstart(formulaField.getText()));//=
		opebutton[9].setOnAction(event -> formulaField.deletePreviousChar());//←
		for(int i = 0; i < 10; ++i) {
			numbutton[i] = new Button(String.valueOf(i));
			numbutton[i].setPrefSize(buttonsize, buttonsize);
			String buttonText = numbutton[i].getText();
			numbutton[i].setOnAction(event -> formulaField.appendText(buttonText));
		}
		//Pane作成
		BorderPane rootBP = new BorderPane();
		BorderPane centerBP = new BorderPane();
		centerBP.setPrefSize(centersizeW, centersizeH);
		
		//数字ボタンをGridPaneで作成した場合
		GridPane numsGP = new GridPane();
		numsGP.setHgap(10);
		numsGP.setVgap(10);
		numsGP.setPadding(new Insets(10, 10, 10, 10));
		//numsGPに数字ボタンをセット
		for(int i = 0; i < 4; ++i) {
			for(int j = 0; j < 3; ++j) {
				if(i == 0) {
					numsGP.add(numbutton[0], 0, 4);
					break;
				}
				numsGP.add(numbutton[(i-1)*3+j+1], j, 4-i);
			}
		}
		//numsGPに演算子ボタンをセット
		for(int i = 0; i < 2; ++i)numsGP.add(opebutton[i], i+1, 4);
		for(int i = 0; i < 5; ++i)numsGP.add(opebutton[i+2], 3, 4-i);
		for(int i = 0; i < 3; ++i)numsGP.add(opebutton[9-i], i, 0);
		
		
//		//数字ボタンをFlowPaneで作成した
//		FlowPane numsFP = new FlowPane();
//		numsFP.setPadding(new Insets(5, 5, 5, 5));
//		numsFP.setVgap(10);
//		numsFP.setHgap(10);
//		numsFP.setPrefWrapLength(170);
//		numsFP.setStyle("-fx-background-color: DAE6F3;");
//		for(int i = 0; i < 10; ++i)
//			numsFP.getChildren().add(numbutton[i]);
		
		centerBP.setTop(runButton);
		centerBP.setCenter(formulaField);
		centerBP.setBottom(numsGP);
		rootBP.setCenter(centerBP);
		rootBP.setLeft(stack);
		
		//シーン作成
		Scene scene = new Scene(rootBP);
		stage.setScene(scene);
		stage.setTitle("テストアプリ");
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
