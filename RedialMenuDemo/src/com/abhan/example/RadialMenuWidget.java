package com.abhan.example;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class RadialMenuWidget extends View {
	public interface RadialMenuEntry {
		public String getName();
		public String getLabel();
		public int getIcon();
		public List<RadialMenuEntry> getChildren();
		public void menuActiviated();
	}
	private final List<RadialMenuEntry>	menuEntries				= new ArrayList<RadialMenuEntry>();
	private RadialMenuEntry				centerCircle			= null;
	private final float					screen_density			= getContext()
																		.getResources()
																		.getDisplayMetrics().density;
	private int							defaultColor			= Color.rgb(34,
																		96, 120);
	private int							defaultAlpha			= 180;
	private int							wedge2Color				= Color.rgb(50,
																		50, 50);
	private int							wedge2Alpha				= 210;
	private int							outlineColor			= Color.rgb(
																		150,
																		150,
																		150);
	private int							outlineAlpha			= 255;
	private int							selectedColor			= Color.rgb(70,
																		130,
																		180);
	private int							selectedAlpha			= 210;
	private int							disabledColor			= Color.rgb(34,
																		96, 120);
	private int							disabledAlpha			= 100;
	private final int					pictureAlpha			= 255;
	private int							textColor				= Color.rgb(
																		255,
																		255,
																		255);
	private int							textAlpha				= 255;
	private int							headerTextColor			= Color.rgb(
																		255,
																		255,
																		255);
	private int							headerTextAlpha			= 255;
	private int							headerBackgroundColor	= Color.rgb(0,
																		0, 0);
	private int							headerBackgroundAlpha	= 180;
	private int							wedgeQty				= 1;
	private Wedge[]						Wedges					= new Wedge[wedgeQty];
	private Wedge						selected				= null;
	private Wedge						enabled					= null;	
	private Rect[]						iconRect				= new Rect[wedgeQty];
	private int							wedgeQty2				= 1;
	private Wedge[]						Wedges2					= new Wedge[wedgeQty2];
	private Wedge						selected2				= null;	
	private Rect[]						iconRect2				= new Rect[wedgeQty2];
	private RadialMenuEntry				wedge2Data				= null;
	private int							MinSize					= scalePX(35);
	private int							MaxSize					= scalePX(90);
	private int							r2MinSize				= MaxSize
																		+ scalePX(5);
	private int							r2MaxSize				= r2MinSize
																		+ scalePX(45);
	private int							MinIconSize				= scalePX(15);
	private int							MaxIconSize				= scalePX(35);
	private int							cRadius					= MinSize
																		- scalePX(7);
	private int							textSize				= scalePX(15);
	private int							animateTextSize			= textSize;
	private int							xPosition				= scalePX(120);
	private int							yPosition				= scalePX(120);
	private int							xSource					= 0;
	private int							ySource					= 0;
	private boolean						showSource				= false;
	private boolean						inWedge					= false;
	private boolean						inWedge2				= false;
	private boolean						inCircle				= false;
	private boolean						Wedge2Shown				= false;
	private boolean						HeaderBoxBounded		= false;								
	private String						headerString			= null;
	private int							headerTextSize			= textSize;
	private final int					headerBuffer			= scalePX(8);
	private final Rect					textRect				= new Rect();
	private final RectF					textBoxRect				= new RectF();
	private int							headerTextLeft;
	private int							headerTextBottom;
	private RotateAnimation				rotate;
	private AlphaAnimation				blend;
	private ScaleAnimation				scale;
	private TranslateAnimation			move;
	private AnimationSet				spriteAnimation;
	private long						animationSpeed			= 400L;
	private static final int			ANIMATE_IN				= 1;
	private static final int			ANIMATE_OUT				= 2;
	private final int					animateSections			= 4;
	private int							r2VariableSize;
	private boolean						animateOuterIn			= false;
	private boolean						animateOuterOut			= false;
	
	public RadialMenuWidget(Context context) {
		super(context);
		this.xPosition = (getResources().getDisplayMetrics().widthPixels) / 2;
		this.yPosition = (getResources().getDisplayMetrics().heightPixels) / 2;
		determineWedges();
		onOpenAnimation();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int state = e.getAction();
		int eventX = (int) e.getX();
		int eventY = (int) e.getY();
		if (state == MotionEvent.ACTION_DOWN) {
			inWedge = false;
			inWedge2 = false;
			inCircle = false;
			for (int i = 0; i < Wedges.length; i++) {
				Wedge f = Wedges[i];
				double slice = (2 * Math.PI) / wedgeQty;
				double start = (2 * Math.PI) * (0.75) - (slice / 2);
				inWedge = pntInWedge(eventX, eventY, xPosition, yPosition,
						MinSize, MaxSize, (i * slice) + start, slice);
				if (inWedge == true) {
					selected = f;
					break;
				}
			}
			if (Wedge2Shown == true) {
				for (int i = 0; i < Wedges2.length; i++) {
					Wedge f = Wedges2[i];
					double slice = (2 * Math.PI) / wedgeQty2;
					double start = (2 * Math.PI) * (0.75) - (slice / 2);
					inWedge2 = pntInWedge(eventX, eventY, xPosition, yPosition,
							r2MinSize, r2MaxSize, (i * slice) + start, slice);
					if (inWedge2 == true) {
						selected2 = f;
						break;
					}
				}
			}
			inCircle = pntInCircle(eventX, eventY, xPosition, yPosition,
					cRadius);
		}
		else
			if (state == MotionEvent.ACTION_UP) {
				if (inCircle == true) {
					if (Wedge2Shown == true) {
						enabled = null;
						animateOuterIn = true;
					}
					selected = null;
					android.util.Log.i("RadialMenuWidget", "" + centerCircle.getName() + " pressed.");
					centerCircle.menuActiviated();
				}
				else
					if (selected != null) {
						for (int i = 0; i < Wedges.length; i++) {
							Wedge f = Wedges[i];
							if (f == selected) {
								if (enabled != null) {
									android.util.Log.i("RadialMenuWidget", "Closing outer ring");
									enabled = null;
									animateOuterIn = true; 
								}
								else {
									android.util.Log.i("RadialMenuWidget", "" + menuEntries.get(i).getName()
											+ " pressed.");
									menuEntries.get(i).menuActiviated();
									if (menuEntries.get(i).getChildren() != null) {
										determineOuterWedges(menuEntries.get(i));
										enabled = f;
										animateOuterOut = true;
									}
									else {
										Wedge2Shown = false;
									}
								}
								selected = null;
							}
						}
					}
					else
						if (selected2 != null) {
							for (int i = 0; i < Wedges2.length; i++) {
								Wedge f = Wedges2[i];
								if (f == selected2) {
									animateOuterIn = true;
									enabled = null;
									selected = null;
								}
							}
						}
						else {
							android.util.Log.i("RadialMenuWidget", "Area outside rings pressed.");
						}
				selected2 = null;
				inCircle = false;
			}
		invalidate();
		return true;
	}
	
	@Override
	protected void onDraw(Canvas c) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(3);
		if (showSource == true) {
			paint.setColor(outlineColor);
			paint.setAlpha(outlineAlpha);
			paint.setStyle(Paint.Style.STROKE);
			c.drawCircle(xSource, ySource, cRadius / 10, paint);
			paint.setColor(selectedColor);
			paint.setAlpha(selectedAlpha);
			paint.setStyle(Paint.Style.FILL);
			c.drawCircle(xSource, ySource, cRadius / 10, paint);
		}
		for (int i = 0; i < Wedges.length; i++) {
			Wedge f = Wedges[i];
			paint.setColor(outlineColor);
			paint.setAlpha(outlineAlpha);
			paint.setStyle(Paint.Style.STROKE);
			c.drawPath(f, paint);
			if (f == enabled && Wedge2Shown == true) {
				paint.setColor(wedge2Color);
				paint.setAlpha(wedge2Alpha);
				paint.setStyle(Paint.Style.FILL);
				c.drawPath(f, paint);
			}
			else
				if (f != enabled && Wedge2Shown == true) {
					paint.setColor(disabledColor);
					paint.setAlpha(disabledAlpha);
					paint.setStyle(Paint.Style.FILL);
					c.drawPath(f, paint);
				}
				else
					if (f == enabled && Wedge2Shown == false) {
						paint.setColor(wedge2Color);
						paint.setAlpha(wedge2Alpha);
						paint.setStyle(Paint.Style.FILL);
						c.drawPath(f, paint);
					}
					else
						if (f == selected) {
							paint.setColor(wedge2Color);
							paint.setAlpha(wedge2Alpha);
							paint.setStyle(Paint.Style.FILL);
							c.drawPath(f, paint);
						}
						else {
							paint.setColor(defaultColor);
							paint.setAlpha(defaultAlpha);
							paint.setStyle(Paint.Style.FILL);
							c.drawPath(f, paint);
						}
			Rect rf = iconRect[i];
			if ((menuEntries.get(i).getIcon() != 0)
					&& (menuEntries.get(i).getLabel() != null)) {
				String menuItemName = menuEntries.get(i).getLabel();
				String[] stringArray = menuItemName.split("\n");
				paint.setColor(textColor);
				if (f != enabled && Wedge2Shown == true) {
					paint.setAlpha(disabledAlpha);
				}
				else {
					paint.setAlpha(textAlpha);
				}
				paint.setStyle(Paint.Style.FILL);
				paint.setTextSize(textSize);
				Rect rect = new Rect();
				float textHeight = 0;
				for (int j = 0; j < stringArray.length; j++) {
					paint.getTextBounds(stringArray[j], 0,
							stringArray[j].length(), rect);
					textHeight = textHeight + (rect.height() + 3);
				}
				Rect rf2 = new Rect();
				rf2.set(rf.left, rf.top - ((int) textHeight / 2), rf.right,
						rf.bottom - ((int) textHeight / 2));
				float textBottom = rf2.bottom;
				for (int j = 0; j < stringArray.length; j++) {
					paint.getTextBounds(stringArray[j], 0,
							stringArray[j].length(), rect);
					float textLeft = rf.centerX() - rect.width() / 2;
					textBottom = textBottom + (rect.height() + 3);
					c.drawText(stringArray[j], textLeft - rect.left, textBottom
							- rect.bottom, paint);
				}
				Drawable drawable = getResources().getDrawable(
						menuEntries.get(i).getIcon());
				drawable.setBounds(rf2);
				if (f != enabled && Wedge2Shown == true) {
					drawable.setAlpha(disabledAlpha);
				}
				else {
					drawable.setAlpha(pictureAlpha);
				}
				drawable.draw(c);
			}
			else
				if (menuEntries.get(i).getIcon() != 0) {
					Drawable drawable = getResources().getDrawable(
							menuEntries.get(i).getIcon());
					drawable.setBounds(rf);
					if (f != enabled && Wedge2Shown == true) {
						drawable.setAlpha(disabledAlpha);
					}
					else {
						drawable.setAlpha(pictureAlpha);
					}
					drawable.draw(c);
				}
				else {
					paint.setColor(textColor);
					if (f != enabled && Wedge2Shown == true) {
						paint.setAlpha(disabledAlpha);
					}
					else {
						paint.setAlpha(textAlpha);
					}
					paint.setStyle(Paint.Style.FILL);
					paint.setTextSize(textSize);
					String menuItemName = menuEntries.get(i).getLabel();
					String[] stringArray = menuItemName.split("\n");
					Rect rect = new Rect();
					float textHeight = 0;
					for (int j = 0; j < stringArray.length; j++) {
						paint.getTextBounds(stringArray[j], 0,
								stringArray[j].length(), rect);
						textHeight = textHeight + (rect.height() + 3);
					}
					float textBottom = rf.centerY() - (textHeight / 2);
					for (int j = 0; j < stringArray.length; j++) {
						paint.getTextBounds(stringArray[j], 0,
								stringArray[j].length(), rect);
						float textLeft = rf.centerX() - rect.width() / 2;
						textBottom = textBottom + (rect.height() + 3);
						c.drawText(stringArray[j], textLeft - rect.left,
								textBottom - rect.bottom, paint);
					}
				}
		}
		if (animateOuterIn == true) {
			animateOuterWedges(ANIMATE_IN);
		}
		else
			if (animateOuterOut == true) {
				animateOuterWedges(ANIMATE_OUT);
			}
		if (Wedge2Shown == true) {
			for (int i = 0; i < Wedges2.length; i++) {
				Wedge f = Wedges2[i];
				paint.setColor(outlineColor);
				paint.setAlpha(outlineAlpha);
				paint.setStyle(Paint.Style.STROKE);
				c.drawPath(f, paint);
				if (f == selected2) {
					paint.setColor(selectedColor);
					paint.setAlpha(selectedAlpha);
					paint.setStyle(Paint.Style.FILL);
					c.drawPath(f, paint);
				}
				else {
					paint.setColor(wedge2Color);
					paint.setAlpha(wedge2Alpha);
					paint.setStyle(Paint.Style.FILL);
					c.drawPath(f, paint);
				}
				Rect rf = iconRect2[i];
				if ((wedge2Data.getChildren().get(i).getIcon() != 0)
						&& (wedge2Data.getChildren().get(i).getLabel() != null)) {
					String menuItemName = wedge2Data.getChildren().get(i)
							.getLabel();
					String[] stringArray = menuItemName.split("\n");
					paint.setColor(textColor);
					paint.setAlpha(textAlpha);
					paint.setStyle(Paint.Style.FILL);
					paint.setTextSize(animateTextSize);
					Rect rect = new Rect();
					float textHeight = 0;
					for (int j = 0; j < stringArray.length; j++) {
						paint.getTextBounds(stringArray[j], 0,
								stringArray[j].length(), rect);
						textHeight = textHeight + (rect.height() + 3);
					}
					Rect rf2 = new Rect();
					rf2.set(rf.left, rf.top - ((int) textHeight / 2), rf.right,
							rf.bottom - ((int) textHeight / 2));
					float textBottom = rf2.bottom;
					for (int j = 0; j < stringArray.length; j++) {
						paint.getTextBounds(stringArray[j], 0,
								stringArray[j].length(), rect);
						float textLeft = rf.centerX() - rect.width() / 2;
						textBottom = textBottom + (rect.height() + 3);
						c.drawText(stringArray[j], textLeft - rect.left,
								textBottom - rect.bottom, paint);
					}
					Drawable drawable = getResources().getDrawable(
							wedge2Data.getChildren().get(i).getIcon());
					drawable.setBounds(rf2);
					drawable.setAlpha(pictureAlpha);
					drawable.draw(c);
				}
				else
					if (wedge2Data.getChildren().get(i).getIcon() != 0) {
						Drawable drawable = getResources().getDrawable(
								wedge2Data.getChildren().get(i).getIcon());
						drawable.setBounds(rf);
						drawable.setAlpha(pictureAlpha);
						drawable.draw(c);
					}
					else {
						paint.setColor(textColor);
						paint.setAlpha(textAlpha);
						paint.setStyle(Paint.Style.FILL);
						paint.setTextSize(animateTextSize);
						String menuItemName = wedge2Data.getChildren().get(i)
								.getLabel();
						String[] stringArray = menuItemName.split("\n");
						Rect rect = new Rect();
						float textHeight = 0;
						for (int j = 0; j < stringArray.length; j++) {
							paint.getTextBounds(stringArray[j], 0,
									stringArray[j].length(), rect);
							textHeight = textHeight + (rect.height() + 3);
						}
						float textBottom = rf.centerY() - (textHeight / 2);
						for (int j = 0; j < stringArray.length; j++) {
							paint.getTextBounds(stringArray[j], 0,
									stringArray[j].length(), rect);
							float textLeft = rf.centerX() - rect.width() / 2;
							textBottom = textBottom + (rect.height() + 3);
							c.drawText(stringArray[j], textLeft - rect.left,
									textBottom - rect.bottom, paint);
						}
					}
			}
		}
		paint.setColor(outlineColor);
		paint.setAlpha(outlineAlpha);
		paint.setStyle(Paint.Style.STROKE);
		c.drawCircle(xPosition, yPosition, cRadius, paint);
		if (inCircle == true) {
			paint.setColor(selectedColor);
			paint.setAlpha(selectedAlpha);
			paint.setStyle(Paint.Style.FILL);
			c.drawCircle(xPosition, yPosition, cRadius, paint);
			onCloseAnimation();
		}
		else {
			paint.setColor(defaultColor);
			paint.setAlpha(defaultAlpha);
			paint.setStyle(Paint.Style.FILL);
			c.drawCircle(xPosition, yPosition, cRadius, paint);
		}
		if ((centerCircle.getIcon() != 0) && (centerCircle.getLabel() != null)) {
			String menuItemName = centerCircle.getLabel();
			String[] stringArray = menuItemName.split("\n");
			paint.setColor(textColor);
			paint.setAlpha(textAlpha);
			paint.setStyle(Paint.Style.FILL);
			paint.setTextSize(textSize);
			Rect rectText = new Rect();
			Rect rectIcon = new Rect();
			Drawable drawable = getResources().getDrawable(
					centerCircle.getIcon());
			int h = getIconSize(drawable.getIntrinsicHeight(), MinIconSize,
					MaxIconSize);
			int w = getIconSize(drawable.getIntrinsicWidth(), MinIconSize,
					MaxIconSize);
			rectIcon.set(xPosition - w / 2, yPosition - h / 2, xPosition + w
					/ 2, yPosition + h / 2);
			float textHeight = 0;
			for (int j = 0; j < stringArray.length; j++) {
				paint.getTextBounds(stringArray[j], 0, stringArray[j].length(),
						rectText);
				textHeight = textHeight + (rectText.height() + 3);
			}
			rectIcon.set(rectIcon.left, rectIcon.top - ((int) textHeight / 2),
					rectIcon.right, rectIcon.bottom - ((int) textHeight / 2));
			float textBottom = rectIcon.bottom;
			for (int j = 0; j < stringArray.length; j++) {
				paint.getTextBounds(stringArray[j], 0, stringArray[j].length(),
						rectText);
				float textLeft = xPosition - rectText.width() / 2;
				textBottom = textBottom + (rectText.height() + 3);
				c.drawText(stringArray[j], textLeft - rectText.left, textBottom
						- rectText.bottom, paint);
			}
			drawable.setBounds(rectIcon);
			drawable.setAlpha(pictureAlpha);
			drawable.draw(c);
		}
		else
			if (centerCircle.getIcon() != 0) {
				Rect rect = new Rect();
				Drawable drawable = getResources().getDrawable(
						centerCircle.getIcon());
				int h = getIconSize(drawable.getIntrinsicHeight(), MinIconSize,
						MaxIconSize);
				int w = getIconSize(drawable.getIntrinsicWidth(), MinIconSize,
						MaxIconSize);
				rect.set(xPosition - w / 2, yPosition - h / 2, xPosition + w
						/ 2, yPosition + h / 2);
				drawable.setBounds(rect);
				drawable.setAlpha(pictureAlpha);
				drawable.draw(c);
			}
			else {
				paint.setColor(textColor);
				paint.setAlpha(textAlpha);
				paint.setStyle(Paint.Style.FILL);
				paint.setTextSize(textSize);
				String menuItemName = centerCircle.getLabel();
				String[] stringArray = menuItemName.split("\n");
				Rect rect = new Rect();
				float textHeight = 0;
				for (int j = 0; j < stringArray.length; j++) {
					paint.getTextBounds(stringArray[j], 0,
							stringArray[j].length(), rect);
					textHeight = textHeight + (rect.height() + 3);
				}
				float textBottom = yPosition - (textHeight / 2);
				for (int j = 0; j < stringArray.length; j++) {
					paint.getTextBounds(stringArray[j], 0,
							stringArray[j].length(), rect);
					float textLeft = xPosition - rect.width() / 2;
					textBottom = textBottom + (rect.height() + 3);
					c.drawText(stringArray[j], textLeft - rect.left, textBottom
							- rect.bottom, paint);
				}
			}
		if (headerString != null) {
			paint.setTextSize(headerTextSize);
			paint.getTextBounds(headerString, 0, headerString.length(),
					this.textRect);
			if (HeaderBoxBounded == false) {
				determineHeaderBox();
				HeaderBoxBounded = true;
			}
			paint.setColor(outlineColor);
			paint.setAlpha(outlineAlpha);
			paint.setStyle(Paint.Style.STROKE);
			c.drawRoundRect(this.textBoxRect, scalePX(5), scalePX(5), paint);
			paint.setColor(headerBackgroundColor);
			paint.setAlpha(headerBackgroundAlpha);
			paint.setStyle(Paint.Style.FILL);
			c.drawRoundRect(this.textBoxRect, scalePX(5), scalePX(5), paint);
			paint.setColor(headerTextColor);
			paint.setAlpha(headerTextAlpha);
			paint.setStyle(Paint.Style.FILL);
			paint.setTextSize(headerTextSize);
			c.drawText(headerString, headerTextLeft, headerTextBottom, paint);
		}
	}
	
	private int getIconSize(int iconSize, int minSize, int maxSize) {
		if (iconSize > minSize) {
			if (iconSize > maxSize) {
				return maxSize;
			}
			else {
				return iconSize;
			}
		}
		else {
			return minSize;
		}
	}
	
	private void onOpenAnimation() {
		rotate = new RotateAnimation(0, 360, xPosition, yPosition);
		scale = new ScaleAnimation(0, 1, 0, 1, xPosition, yPosition);
		scale.setInterpolator(new DecelerateInterpolator());
		move = new TranslateAnimation(xSource - xPosition, 0, ySource
				- yPosition, 0);
		spriteAnimation = new AnimationSet(true);
		spriteAnimation.addAnimation(scale);
		spriteAnimation.addAnimation(move);
		spriteAnimation.setDuration(animationSpeed);
		startAnimation(spriteAnimation);
	}
	
	private void onCloseAnimation() {
		rotate = new RotateAnimation(360, 0, xPosition, yPosition);
		scale = new ScaleAnimation(1, 0, 1, 0, xPosition, yPosition);
		scale.setInterpolator(new AccelerateInterpolator());
		move = new TranslateAnimation(0, xSource - xPosition, 0, ySource
				- yPosition);
		spriteAnimation = new AnimationSet(true);
		spriteAnimation.addAnimation(scale);
		spriteAnimation.addAnimation(move);
		spriteAnimation.setDuration(animationSpeed);
		startAnimation(spriteAnimation);
	}
	
	private boolean pntInCircle(double px, double py, double x1, double y1,
			double radius) {
		double diffX = x1 - px;
		double diffY = y1 - py;
		double dist = diffX * diffX + diffY * diffY;
		if (dist < radius * radius) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean pntInWedge(double px, double py, float xRadiusCenter,
			float yRadiusCenter, int innerRadius, int outerRadius,
			double startAngle, double sweepAngle) {
		double diffX = px - xRadiusCenter;
		double diffY = py - yRadiusCenter;
		double angle = Math.atan2(diffY, diffX);
		if (angle < 0)
			angle += (2 * Math.PI);
		if (startAngle >= (2 * Math.PI)) {
			startAngle = startAngle - (2 * Math.PI);
		}
		if ((angle >= startAngle && angle <= startAngle + sweepAngle)
				|| (angle + (2 * Math.PI) >= startAngle && (angle + (2 * Math.PI)) <= startAngle
						+ sweepAngle)) {
			double dist = diffX * diffX + diffY * diffY;
			if (dist < outerRadius * outerRadius
					&& dist > innerRadius * innerRadius) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addMenuEntry(RadialMenuEntry entry) {
		menuEntries.add(entry);
		determineWedges();
		return true;
	}
	
	public boolean setCenterCircle(RadialMenuEntry entry) {
		centerCircle = entry;
		return true;
	}
	
	public void setInnerRingRadius(int InnerRadius, int OuterRadius) {
		this.MinSize = scalePX(InnerRadius);
		this.MaxSize = scalePX(OuterRadius);
		determineWedges();
	}
	
	public void setOuterRingRadius(int InnerRadius, int OuterRadius) {
		this.r2MinSize = scalePX(InnerRadius);
		this.r2MaxSize = scalePX(OuterRadius);
		determineWedges();
	}
	
	public void setCenterCircleRadius(int centerRadius) {
		this.cRadius = scalePX(centerRadius);
		determineWedges();
	}
	
	public void setTextSize(int TextSize) {
		this.textSize = scalePX(TextSize);
		this.animateTextSize = this.textSize;
	}
	
	public void setIconSize(int minIconSize, int maxIconSize) {
		this.MinIconSize = scalePX(minIconSize);
		this.MaxIconSize = scalePX(maxIconSize);
		determineWedges();
	}
	
	public void setCenterLocation(int x, int y) {
		this.xPosition = x;
		this.yPosition = y;
		determineWedges();
		onOpenAnimation();
	}
	
	public void setSourceLocation(int x, int y) {
		this.xSource = x;
		this.ySource = y;
		onOpenAnimation();
	}
	
	public void setShowSourceLocation(boolean showSourceLocation) {
		this.showSource = showSourceLocation;
		onOpenAnimation();
	}
	
	public void setAnimationSpeed(long millis) {
		this.animationSpeed = millis;
		onOpenAnimation();
	}
	
	public void setInnerRingColor(int color, int alpha) {
		this.defaultColor = color;
		this.defaultAlpha = alpha;
	}
	
	public void setOuterRingColor(int color, int alpha) {
		this.wedge2Color = color;
		this.wedge2Alpha = alpha;
	}
	
	public void setOutlineColor(int color, int alpha) {
		this.outlineColor = color;
		this.outlineAlpha = alpha;
	}
	
	public void setSelectedColor(int color, int alpha) {
		this.selectedColor = color;
		this.selectedAlpha = alpha;
	}
	
	public void setDisabledColor(int color, int alpha) {
		this.disabledColor = color;
		this.disabledAlpha = alpha;
	}
	
	public void setTextColor(int color, int alpha) {
		this.textColor = color;
		this.textAlpha = alpha;
	}
	
	public void setHeader(String header, int TextSize) {
		this.headerString = header;
		this.headerTextSize = scalePX(TextSize);
		HeaderBoxBounded = false;
	}
	
	public void setHeaderColors(int TextColor, int TextAlpha, int BgColor,
			int BgAlpha) {
		this.headerTextColor = TextColor;
		this.headerTextAlpha = TextAlpha;
		this.headerBackgroundColor = BgColor;
		this.headerBackgroundAlpha = BgAlpha;
	}
	
	private int scalePX(int dp_size) {
		int px_size = (int) (dp_size * screen_density + 0.5f);
		return px_size;
	}
	
	private void animateOuterWedges(int animation_direction) {
		boolean animationComplete = false;
		float slice2 = 360 / wedgeQty2;
		float start_slice2 = 270 - (slice2 / 2);
		double rSlice2 = (2 * Math.PI) / wedgeQty2;
		double rStart2 = (2 * Math.PI) * (0.75) - (rSlice2 / 2);
		this.Wedges2 = new Wedge[wedgeQty2];
		this.iconRect2 = new Rect[wedgeQty2];
		Wedge2Shown = true;
		int wedgeSizeChange = (r2MaxSize - r2MinSize) / animateSections;
		if (animation_direction == ANIMATE_OUT) {
			if (r2MinSize + r2VariableSize + wedgeSizeChange < r2MaxSize) {
				r2VariableSize += wedgeSizeChange;
			}
			else {
				animateOuterOut = false;
				r2VariableSize = r2MaxSize - r2MinSize;
				animationComplete = true;
			}
			this.animateTextSize = (textSize / animateSections)
					* (r2VariableSize / wedgeSizeChange);
			for (int i = 0; i < Wedges2.length; i++) {
				this.Wedges2[i] = new Wedge(xPosition, yPosition, r2MinSize,
						r2MinSize + r2VariableSize,
						(i * slice2) + start_slice2, slice2);
				float xCenter = (float) (Math
						.cos(((rSlice2 * i) + (rSlice2 * 0.5)) + rStart2)
						* (r2MinSize + r2VariableSize + r2MinSize) / 2)
						+ xPosition;
				float yCenter = (float) (Math
						.sin(((rSlice2 * i) + (rSlice2 * 0.5)) + rStart2)
						* (r2MinSize + r2VariableSize + r2MinSize) / 2)
						+ yPosition;
				int h = MaxIconSize;
				int w = MaxIconSize;
				if (wedge2Data.getChildren().get(i).getIcon() != 0) {
					Drawable drawable = getResources().getDrawable(
							wedge2Data.getChildren().get(i).getIcon());
					h = getIconSize(drawable.getIntrinsicHeight(), MinIconSize,
							MaxIconSize);
					w = getIconSize(drawable.getIntrinsicWidth(), MinIconSize,
							MaxIconSize);
				}
				if (r2VariableSize < h) {
					h = r2VariableSize;
				}
				if (r2VariableSize < w) {
					w = r2VariableSize;
				}
				this.iconRect2[i] = new Rect((int) xCenter - w / 2,
						(int) yCenter - h / 2, (int) xCenter + w / 2,
						(int) yCenter + h / 2);
				int widthOffset = MaxSize;
				if (widthOffset < this.textRect.width() / 2) {
					widthOffset = this.textRect.width() / 2 + scalePX(3);
				}
				this.textBoxRect.set((xPosition - (widthOffset)), yPosition
						- (r2MinSize + r2VariableSize) - headerBuffer
						- this.textRect.height() - scalePX(3),
						(xPosition + (widthOffset)),
						(yPosition - (r2MinSize + r2VariableSize)
								- headerBuffer + scalePX(3)));
				this.headerTextBottom = yPosition
						- (r2MinSize + r2VariableSize) - headerBuffer
						- this.textRect.bottom;
			}
		}
		else
			if (animation_direction == ANIMATE_IN) {
				if (r2MinSize < r2MaxSize - r2VariableSize - wedgeSizeChange) {
					r2VariableSize += wedgeSizeChange;
				}
				else {
					animateOuterIn = false;
					r2VariableSize = r2MaxSize;
					animationComplete = true;
				}
				this.animateTextSize = textSize
						- ((textSize / animateSections) * (r2VariableSize / wedgeSizeChange));
				for (int i = 0; i < Wedges2.length; i++) {
					this.Wedges2[i] = new Wedge(xPosition, yPosition,
							r2MinSize, r2MaxSize - r2VariableSize, (i * slice2)
									+ start_slice2, slice2);
					float xCenter = (float) (Math
							.cos(((rSlice2 * i) + (rSlice2 * 0.5)) + rStart2)
							* (r2MaxSize - r2VariableSize + r2MinSize) / 2)
							+ xPosition;
					float yCenter = (float) (Math
							.sin(((rSlice2 * i) + (rSlice2 * 0.5)) + rStart2)
							* (r2MaxSize - r2VariableSize + r2MinSize) / 2)
							+ yPosition;
					int h = MaxIconSize;
					int w = MaxIconSize;
					if (wedge2Data.getChildren().get(i).getIcon() != 0) {
						Drawable drawable = getResources().getDrawable(
								wedge2Data.getChildren().get(i).getIcon());
						h = getIconSize(drawable.getIntrinsicHeight(),
								MinIconSize, MaxIconSize);
						w = getIconSize(drawable.getIntrinsicWidth(),
								MinIconSize, MaxIconSize);
					}
					if (r2MaxSize - r2MinSize - r2VariableSize < h) {
						h = r2MaxSize - r2MinSize - r2VariableSize;
					}
					if (r2MaxSize - r2MinSize - r2VariableSize < w) {
						w = r2MaxSize - r2MinSize - r2VariableSize;
					}
					this.iconRect2[i] = new Rect((int) xCenter - w / 2,
							(int) yCenter - h / 2, (int) xCenter + w / 2,
							(int) yCenter + h / 2);
					int heightOffset = r2MaxSize - r2VariableSize;
					int widthOffset = MaxSize;
					if (MaxSize > r2MaxSize - r2VariableSize) {
						heightOffset = MaxSize;
					}
					if (widthOffset < this.textRect.width() / 2) {
						widthOffset = this.textRect.width() / 2 + scalePX(3);
					}
					this.textBoxRect
							.set((xPosition - (widthOffset)),
									yPosition - (heightOffset) - headerBuffer
											- this.textRect.height()
											- scalePX(3),
									(xPosition + (widthOffset)),
									(yPosition - (heightOffset) - headerBuffer + scalePX(3)));
					this.headerTextBottom = yPosition - (heightOffset)
							- headerBuffer - this.textRect.bottom;
				}
			}
		if (animationComplete == true) {
			r2VariableSize = 0;
			this.animateTextSize = textSize;
			if (animation_direction == ANIMATE_IN) {
				Wedge2Shown = false;
			}
		}
		invalidate();
	}
	
	private void determineWedges() {
		int entriesQty = menuEntries.size();
		if (entriesQty > 0) {
			wedgeQty = entriesQty;
			float degSlice = 360 / wedgeQty;
			float start_degSlice = 270 - (degSlice / 2);
			double rSlice = (2 * Math.PI) / wedgeQty;
			double rStart = (2 * Math.PI) * (0.75) - (rSlice / 2);
			this.Wedges = new Wedge[wedgeQty];
			this.iconRect = new Rect[wedgeQty];
			for (int i = 0; i < Wedges.length; i++) {
				this.Wedges[i] = new Wedge(xPosition, yPosition, MinSize,
						MaxSize, (i * degSlice) + start_degSlice, degSlice);
				float xCenter = (float) (Math
						.cos(((rSlice * i) + (rSlice * 0.5)) + rStart)
						* (MaxSize + MinSize) / 2) + xPosition;
				float yCenter = (float) (Math
						.sin(((rSlice * i) + (rSlice * 0.5)) + rStart)
						* (MaxSize + MinSize) / 2) + yPosition;
				int h = MaxIconSize;
				int w = MaxIconSize;
				if (menuEntries.get(i).getIcon() != 0) {
					Drawable drawable = getResources().getDrawable(
							menuEntries.get(i).getIcon());
					h = getIconSize(drawable.getIntrinsicHeight(), MinIconSize,
							MaxIconSize);
					w = getIconSize(drawable.getIntrinsicWidth(), MinIconSize,
							MaxIconSize);
				}
				this.iconRect[i] = new Rect((int) xCenter - w / 2,
						(int) yCenter - h / 2, (int) xCenter + w / 2,
						(int) yCenter + h / 2);
			}
			invalidate();
		}
	}
	
	private void determineOuterWedges(RadialMenuEntry entry) {
		int entriesQty = entry.getChildren().size();
		wedgeQty2 = entriesQty;
		float degSlice2 = 360 / wedgeQty2;
		float start_degSlice2 = 270 - (degSlice2 / 2);
		double rSlice2 = (2 * Math.PI) / wedgeQty2;
		double rStart2 = (2 * Math.PI) * (0.75) - (rSlice2 / 2);
		this.Wedges2 = new Wedge[wedgeQty2];
		this.iconRect2 = new Rect[wedgeQty2];
		for (int i = 0; i < Wedges2.length; i++) {
			this.Wedges2[i] = new Wedge(xPosition, yPosition, r2MinSize,
					r2MaxSize, (i * degSlice2) + start_degSlice2, degSlice2);
			float xCenter = (float) (Math.cos(((rSlice2 * i) + (rSlice2 * 0.5))
					+ rStart2)
					* (r2MaxSize + r2MinSize) / 2)
					+ xPosition;
			float yCenter = (float) (Math.sin(((rSlice2 * i) + (rSlice2 * 0.5))
					+ rStart2)
					* (r2MaxSize + r2MinSize) / 2)
					+ yPosition;
			int h = MaxIconSize;
			int w = MaxIconSize;
			if (entry.getChildren().get(i).getIcon() != 0) {
				Drawable drawable = getResources().getDrawable(
						entry.getChildren().get(i).getIcon());
				h = getIconSize(drawable.getIntrinsicHeight(), MinIconSize,
						MaxIconSize);
				w = getIconSize(drawable.getIntrinsicWidth(), MinIconSize,
						MaxIconSize);
			}
			this.iconRect2[i] = new Rect((int) xCenter - w / 2, (int) yCenter
					- h / 2, (int) xCenter + w / 2, (int) yCenter + h / 2);
		}
		this.wedge2Data = entry;
		invalidate();
	}
	
	private void determineHeaderBox() {
		this.headerTextLeft = xPosition - this.textRect.width() / 2;
		this.headerTextBottom = yPosition - (MaxSize) - headerBuffer
				- this.textRect.bottom;
		int offset = MaxSize;
		if (offset < this.textRect.width() / 2) {
			offset = this.textRect.width() / 2 + scalePX(3);
		}
		this.textBoxRect.set((xPosition - (offset)), yPosition - (MaxSize)
				- headerBuffer - this.textRect.height() - scalePX(3),
				(xPosition + (offset)),
				(yPosition - (MaxSize) - headerBuffer + scalePX(3)));
	}
	
	public class Wedge extends Path {
		private final int	x, y;
		private final int	InnerSize, OuterSize;
		private final float	StartArc;
		private final float	ArcWidth;
		
		private Wedge(int x, int y, int InnerSize, int OuterSize,
				float StartArc, float ArcWidth) {
			super();
			if (StartArc >= 360) {
				StartArc = StartArc - 360;
			}
			this.x = x;
			this.y = y;
			this.InnerSize = InnerSize;
			this.OuterSize = OuterSize;
			this.StartArc = StartArc;
			this.ArcWidth = ArcWidth;
			this.buildPath();
		}
		
		private void buildPath() {
			final RectF rect = new RectF();
			final RectF rect2 = new RectF();
			rect.set(this.x - this.InnerSize, this.y - this.InnerSize, this.x
					+ this.InnerSize, this.y + this.InnerSize);
			rect2.set(this.x - this.OuterSize, this.y - this.OuterSize, this.x
					+ this.OuterSize, this.y + this.OuterSize);
			this.reset();
			this.arcTo(rect2, StartArc, ArcWidth);
			this.arcTo(rect, StartArc + ArcWidth, -ArcWidth);
			this.close();
		}
	}
}