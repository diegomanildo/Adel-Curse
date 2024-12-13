package game.items;

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
            item.removeFromOwner();
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
        owner.setBulletTexturePath("skeleton/bullet.png", true);
        timerThread.start();
    }

    @Override
    public void removeFromOwner() {
        super.removeFromOwner();
        owner.setBulletTexturePath(previousBulletTexturePath, true);
    }

    public String getPreviousBulletTexturePath() {
        return previousBulletTexturePath;
    }
}
