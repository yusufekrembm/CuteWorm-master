package com.atilsamancioglu.survivorbirdstarter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture cuteWorm;
	Texture black;
	Texture spiderEnemy1;
	Texture spiderEnemy2;
	Texture spiderEnemy3;
	float wormX=0;
	float wormY=0;
	int gameState = 0;
	float velocity=0;
	float gravity=0.1f;
	int numberOfEnemies=4;
	Random random;
	Circle wormCircle;
	Rectangle rectangle;
	float [] enemyX = new float[numberOfEnemies];
	float distance = 0;
	float enemyVelocity=2;
	float [] enemyOffset = new float[numberOfEnemies];
	float [] enemyOffset2 = new float[numberOfEnemies];
	float [] enemyOffset3 = new float[numberOfEnemies];
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	ShapeRenderer shapeRenderer;
	int score = 0;
	int scoredEnemy;
	BitmapFont font;
	BitmapFont font2;
	float blackX =0;
	float blackY = 0;



	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png"); // push object to Texture show image background
		cuteWorm = new Texture("worm.png"); // push object to Texture show image cuteWorm
		black = new Texture("black.png"); // push object to Texture show image black
		spiderEnemy1 = new Texture("enemyspider.png"); // push object to Texture show image spider enemy
		spiderEnemy2 = new Texture("enemyspider.png"); // push object to Texture show image spider enemy
		spiderEnemy3 = new Texture("enemyspider.png"); // push object to Texture show image spider enemy

		distance = Gdx.graphics.getWidth()/2;
		random = new Random();
		wormX = Gdx.graphics.getWidth()/3- cuteWorm.getHeight();
		wormY = Gdx.graphics.getHeight()/3; // worm place on app
		blackX = 0;
		blackY = 1071;
		wormCircle = new Circle();
		rectangle = new Rectangle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);
		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);

		for(int i=0;i<numberOfEnemies;i++){
			enemyOffset[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
			enemyOffset2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
			enemyOffset3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
			enemyX[i] = Gdx.graphics.getWidth()-spiderEnemy1.getWidth()/2+i*distance; // spawning 4 enemy array object behind to behind
			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}
	}

	@Override
	public void render () { // drawing something btc...
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); // value of object place draw

		if(gameState==1){
			if(enemyX[scoredEnemy] < Gdx.graphics.getWidth()/3- cuteWorm.getHeight()){
				score++;
				if(scoredEnemy < numberOfEnemies-1){
					scoredEnemy++;
				}else{
					scoredEnemy=0;
				}
			}
			if(Gdx.input.justTouched()){
				velocity=velocity-gravity*50;
				wormY = wormY + velocity;
			}
			for(int i=0;i<numberOfEnemies;i++){

				if(enemyX[i] < Gdx.graphics.getWidth() / 12){
					enemyX[i] = enemyX[i] + numberOfEnemies*distance; // distance
					enemyOffset[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);
					enemyOffset2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);
					enemyOffset3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);
				}else{
					enemyX[i] = enemyX[i]-enemyVelocity*5; // velocity of enemies
				}

				batch.draw(spiderEnemy1, enemyX[i], Gdx.graphics.getHeight()/2+enemyOffset[i], Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 10);
				batch.draw(spiderEnemy2, enemyX[i], Gdx.graphics.getHeight()/2+enemyOffset2[i], Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 10);
				batch.draw(spiderEnemy3, enemyX[i], Gdx.graphics.getHeight()/2+enemyOffset3[i], Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 10);

				enemyCircles[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth() / 24,Gdx.graphics.getHeight()/2+enemyOffset[i]+Gdx.graphics.getHeight() / 15,Gdx.graphics.getWidth() / 60);
				enemyCircles2[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth() / 24,Gdx.graphics.getHeight()/2+enemyOffset2[i]+Gdx.graphics.getHeight() / 15,Gdx.graphics.getWidth() / 60);
				enemyCircles3[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth() / 24,Gdx.graphics.getHeight()/2+enemyOffset3[i]+Gdx.graphics.getHeight() / 15,Gdx.graphics.getWidth() / 60);
			}

			if(wormY>10){
				velocity=velocity+gravity;
				wormY = wormY - velocity;
			}else{
				gameState =2;
			}
		}else if(gameState==0){
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if(gameState==2){
			font2.draw(batch,"Game Over..! Please touch to screen for restart",100,Gdx.graphics.getHeight()/2);

			if(Gdx.input.justTouched()){
			gameState=1;
			wormY = Gdx.graphics.getHeight()/3; // worm place on app
				for(int i=0;i<numberOfEnemies;i++){
					enemyOffset[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffset2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffset3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
					enemyX[i] = Gdx.graphics.getWidth()-spiderEnemy1.getWidth()/2+i*distance; // spawning 4 enemy array object behind to behind
					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
				}
				velocity=0;
				scoredEnemy =0;
				score=0;
			}
		}



		batch.draw(cuteWorm,wormX,wormY,Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/10);
		batch.draw(black,blackX, blackY,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/50);
		font.draw(batch,String.valueOf(score),100,200);
		batch.end();

		wormCircle.set(wormX+Gdx.graphics.getWidth()/20,wormY+Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30); // worm collider
		rectangle.set(blackX,blackY,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/50); // rectangle collider
		 // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		 // shapeRenderer.setColor(Color.YELLOW);
		// shapeRenderer.rect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);


		for(int i = 0; i<numberOfEnemies;i++){
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 24,Gdx.graphics.getHeight()/2+enemyOffset[i]+Gdx.graphics.getHeight() / 15,Gdx.graphics.getWidth() / 60);
			// shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 24,Gdx.graphics.getHeight()/2+enemyOffset2[i]+Gdx.graphics.getHeight() / 15,Gdx.graphics.getWidth() / 60);
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 24,Gdx.graphics.getHeight()/2+enemyOffset3[i]+Gdx.graphics.getHeight() / 15,Gdx.graphics.getWidth() / 60);
			if(Intersector.overlaps(wormCircle,enemyCircles[i])|| Intersector.overlaps(wormCircle,enemyCircles2[i])|| Intersector.overlaps(wormCircle,enemyCircles3[i])|| Intersector.overlaps(wormCircle,rectangle)){
				System.out.println("Collision Detection");
				gameState = 2;
			}
		}
		  //shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
	}
}
