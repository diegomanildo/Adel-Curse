package game.entities.items;

import utilities.Utils;

public class SkeletonMask extends Item {
    private static final int SECONDS = 10;

    private static class TimerThread extends Thread {
        private final SkeletonMask item;

        public TimerThread(SkeletonMask item) {
            this.item = item;
        }

        @Override
        public void run() {
            super.run();
            Utils.sleep(SECONDS * 1000);
            item.getOwner().removeItem(item);
            item.getOwner().setBulletTexturePath(item.getPreviousBulletTexturePath());
        }
    }

    private final TimerThread timerThread = new TimerThread(this);
    private String previousBulletTexturePath;

    public SkeletonMask() {
        super(ItemQuality.Common, "", "skeletonMask.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        previousBulletTexturePath = owner.getBulletTexturePath();
        owner.setBulletTexturePath("skeleton/bullet.png");
        timerThread.start();
    }

    @Override
    public void removeFromOwner() {
        super.removeFromOwner();
        owner.setBulletTexturePath("adel/bullet.png");
    }

    public String getPreviousBulletTexturePath() {
        return previousBulletTexturePath;
    }
}
