public class Lives extends Sprite{
    public Lives(int cnt){
        super(10 + (cnt - 1) * 30, 10, 0, 0, 0, 0);
        sprite = loadImage("/imgs/"+"heart.png");
        this.width = (int) (sprite.getWidth() * scaleWidth);
		this.height = (int) (sprite.getHeight() * scaleHeight);
    }
}
