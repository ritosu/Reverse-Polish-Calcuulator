
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import java.util.List;

public class GUI extends Application {
	private TextArea formulaField;
	private TextField result;
	private TextArea stack;
	private DrawGraphics drawGraphics;
	Pane graphP = new Pane();
	//イコール（＝）が押されたとき、計算・表示する
	private void setResult() {
		String str = formulaField.getText();
		String value = (new RPCCLI()).calstart(str);
		result.setText(value);
		System.out.println(result.getWidth()+" "+stack.getWidth());
		//グラフ表示
		drawGraphics.getGroup().getChildren().clear();
		drawGraphics.drawAnim(str);
	}
	//演算子が押されたときの処理
	private void clickOperator(String ope) {
		String formula = formulaField.getText();
		if(formula.charAt(formula.length()-1) != '_')
			formula+= '_'+ope+'_';
		else
			formula+= ope+'_';
		formulaField.setText(formula);
	}

	public void start(Stage stage) {
		formulaField = new TextArea();
//		formulaField.setFont(Font.loadFont("file:resources/fonts/Arial-Bold.ttf", 24));
		formulaField.setFont(new Font("Arial Rounded MT Bold", 24));
		formulaField.getStyleClass().add("formula");
		formulaField.setWrapText(true);
		formulaField.setPrefHeight(200);
		result = new TextField();
		result.setFont(new Font("Arial Rounded MT Bold", 30));
		result.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		result.setEditable(false);
		result.getStyleClass().add("result");
		result.setMaxHeight(200);
		stack = new TextArea();
		stack.setPrefSize(100, 460);
		stack.setEditable(false);
		drawGraphics =  new DrawGraphics();
		Background bg = new Background(new BackgroundFill(Color.ALICEBLUE, null, null));
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
		opebutton[8] = new Button("ac");
		opebutton[9] = new Button("←");
		for(int i = 0; i < 10; ++i) {
			opebutton[i].setMaxWidth(Double.MAX_VALUE);
			opebutton[i].setMaxHeight(Double.MAX_VALUE);
			String buttonText = opebutton[i].getText();
			opebutton[i].setOnAction(event -> clickOperator(buttonText));
		}
		opebutton[1].setOnAction(event -> formulaField.appendText("_"));	//␣
		opebutton[2].setOnAction(event -> setResult()); 					//=
		opebutton[8].setOnAction(event -> formulaField.clear());
		opebutton[9].setOnAction(event -> formulaField.deletePreviousChar());//←
		for(int i = 0; i < 10; ++i) {
			numbutton[i] = new Button(String.valueOf(i));
			numbutton[i].setMaxWidth(Double.MAX_VALUE);
			numbutton[i].setMaxHeight(Double.MAX_VALUE);
			String buttonText = numbutton[i].getText();
			numbutton[i].setOnAction(event -> formulaField.appendText(buttonText));
		}
		//Pane作成
		BorderPane rootBP = new BorderPane();
		BorderPane centerBP = new BorderPane();
		GridPane numsGP = new GridPane();
		VBox textVB = new VBox();
//		Pane graphP = new Pane();
		
		graphP.setMinWidth(100);
		graphP.setBackground(bg);
		textVB.setPrefSize(400, 250);
		numsGP.setPrefSize(300, 200);
		numsGP.setHgap(6);
		numsGP.setVgap(6);
		numsGP.setPadding(new Insets(8, 8, 8, 8));
		//numsGPにボタンをセット
		for(int i = 0; i < 4; ++i) {
			for(int j = 0; j < 3; ++j) {
				if(i == 0) {
					numsGP.add(numbutton[0], 0, 4);
					break;
				}
				numsGP.add(numbutton[(i-1)*3+j+1], j, 4-i);
			}
		}
		for(int i = 0; i < 2; ++i)numsGP.add(opebutton[i], i+1, 4);
		for(int i = 0; i < 5; ++i)numsGP.add(opebutton[i+2], 3, 4-i);
		for(int i = 0; i < 3; ++i)numsGP.add(opebutton[9-i], i, 0);
		ColumnConstraints[] ColCon = new ColumnConstraints[4];
		RowConstraints[] RowCon = new RowConstraints[5];
		for(int i = 0; i < 4; ++i)ColCon[i] = new ColumnConstraints();
		for(int i = 0; i < 4; ++i)ColCon[i].setPercentWidth(25);
		for(int i = 0; i < 5; ++i)RowCon[i] = new RowConstraints();
		for(int i = 0; i < 5; ++i)RowCon[i].setPercentHeight(20);
		numsGP.getColumnConstraints().addAll(ColCon);
		numsGP.getRowConstraints().addAll(RowCon);
		//Pane配置
		textVB.getChildren().add(formulaField);
		textVB.getChildren().add(result);
		centerBP.setTop(textVB);
		centerBP.setCenter(numsGP);
		graphP.getChildren().add(drawGraphics.getGroup());
		graphP.setRotate(180.0);
		rootBP.setCenter(centerBP);
		rootBP.setRight(graphP);
		
		
		//シーン作成
		Scene scene = new Scene(rootBP);
		stage.setScene(scene);
		stage.setTitle("電卓");
		stage.setMinWidth(300);
		stage.setMinHeight(460);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
