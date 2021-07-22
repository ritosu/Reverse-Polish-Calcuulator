import javafx.animation.TranslateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
public class DrawGraphics{
	Group group;
	Stack stack = null;
	
	DrawGraphics(){
		group = new Group();
		stack = new Stack();
	}
	public Group getGroup() {
		return drawBase();
	}
	public void drawAnim(String str) {
		drawAnim(str.split("[_ ]"));
	}
	
	private Group drawBase() {
		Line stackbottom = new Line(20, 20, 80, 20);
		Line stackleft = new Line(20, 20, 20, 180);
		Line stackright = new Line(80, 20, 80, 180);
		Line[] stackshape = new Line[3];
		stackshape[0] = stackbottom;
		stackshape[1] = stackleft;
		stackshape[2] = stackright;
		for(int i = 0; i < 3; ++i) {
			stackshape[i].setFill(Color.BLACK);
		}
		for(int i = 0; i < 3; ++i)group.getChildren().add(stackshape[i]);
		return group;
	}
	
	private void drawAnim(String[] str) {
		drawBase();
		int cnt = 0;
		Rectangle[] rect = new Rectangle[str.length];
		TranslateTransition[] pushTT = new TranslateTransition[str.length];
		ParallelTransition[] fadeParT = new ParallelTransition[str.length];
		FadeTransition[] FadT = new FadeTransition[str.length];
		TranslateTransition[] downTT= new TranslateTransition[str.length];
		SequentialTransition SeqT = new SequentialTransition();
		SequentialTransition[] pushSeqT = new SequentialTransition[str.length];
		ParallelTransition[] ParT = new ParallelTransition[str.length];
		
		for(int i = 0; i < str.length; ++i) {
			rect[i] = new Rectangle(60, 30);
			rect[i].setX(20);
			rect[i].setY(200+i*30);
			rect[i].setFill(Color.rgb((30*(i+1))%255, (50*(i+1))%255, (90*(i+1))%255));
			pushSeqT[i] = new SequentialTransition();
			pushTT[i]= new TranslateTransition(Duration.seconds(2), rect[i]);
			if(isNumber(str[i])) {
				pushTT[i].setToY(-180+cnt*30-i*30);
				cnt++;
				stack.push(String.valueOf(i));
			}else {
				int ite1 = Integer.valueOf(stack.pop());
				int ite2 = Integer.valueOf(stack.pop());
				fadeParT[i] = new ParallelTransition();
				FadT[ite1] = new FadeTransition(Duration.seconds(1.5), rect[ite1]);
				FadT[ite2] = new FadeTransition(Duration.seconds(1.5), rect[ite2]);
				FadT[ite1].setToValue(0);
				FadT[ite2].setToValue(0);
				fadeParT[i].getChildren().add(FadT[ite1]);
				fadeParT[i].getChildren().add(FadT[ite2]);
				cnt-=2;
				pushTT[i].setToY(-180+cnt*30-i*30);
				pushSeqT[i].getChildren().add(fadeParT[i]);
				stack.push(String.valueOf(i));
				cnt++;
			}
			pushSeqT[i].getChildren().add(pushTT[i]);
			group.getChildren().add(rect[i]);
		}
		//y-30する　一斉に下げる用
		for(int i = 0; i < str.length; ++i) {
			downTT[i] = new TranslateTransition(Duration.seconds(2), rect[i]);
			downTT[i].setToY(-30);
		}
		//一斉に下げるためにParallelTransitionに入れる
		for(int i = 0; i < str.length; ++i) {
			ParT[i] = new ParallelTransition();
			for(int j = i+1; j < str.length; ++j)
				ParT[i].getChildren().add(downTT[j]);
		}
		//全部のアニメーションをSequentialTransitionに入れて逐次実行
		for(int i = 0; i < str.length; ++i) {
			SeqT.getChildren().add(pushSeqT[i]);
			if(i!=str.length-1)SeqT.getChildren().add(ParT[i]);
		}
		SeqT.play();
	}    
	
	private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}