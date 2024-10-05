    package game.rooms;

    import game.GameScreen;
    import game.entities.GameEntity;
    import game.utilities.EntityClassList;

    import java.util.Random;

    public abstract class EnemyRoom extends Room {
        private final EntityClassList entitiesClasses;
        private static Random random = new Random();
        private final int quantityOfEntities;
        private boolean spawned = false;

        protected EnemyRoom(String mapFile, EntityClassList entitiesClasses, int quantityOfEntities) {
            super(mapFile);
            this.entitiesClasses = entitiesClasses;
            this.quantityOfEntities = quantityOfEntities;
        }

        @Override
        public void show() {
            super.show();
            if (!spawned) {
                generateEntities(quantityOfEntities);
                spawned = true;
            } else {
                for (GameEntity entity : entities) {
                    entity.setVisible(true);
                }
            }
        }

        private void generateEntities(int quantity) {
            while (quantity > 0) {
                generateEntity();
                quantity--;
            }
        }

        private void generateEntity() {
            float x = 0, y = 0;
            int maxAttempts = 100;
            int attempts = 0;

            do {
                x = random.nextFloat() * getWidth();
                attempts++;
            } while (!verifyX(x) && attempts < maxAttempts);

            System.out.println(verifyX(x));

            attempts = 0;

            do {
                y = random.nextFloat() * getHeight();
                attempts++;
            } while (!verifyY(y) && attempts < maxAttempts);

            System.out.println(verifyY(y));

            GameEntity entity = getRandomEntityAt(x, y);

            System.out.println("into hitbox: " + GameScreen.game.getLevel().getHitbox().collidesWith(entity.getHitbox()));
            System.out.println("---------------");

            if(GameScreen.game.getLevel().getHitbox().collidesWith(entity.getHitbox())){
                entity.setPosition(GameScreen.game.getLevel().getHitbox().getLeft(), GameScreen.game.getLevel().getHitbox().getBottom());
            }

            createEntity(entity);
        }

        private boolean verifyX(float x) {
            return x > GameScreen.game.getLevel().getHitbox().getLeft() &&
                    x < GameScreen.game.getLevel().getHitbox().getRight();
        }

        private boolean verifyY(float y) {
            return y > GameScreen.game.getLevel().getHitbox().getBottom() &&
                    y < GameScreen.game.getLevel().getHitbox().getTop();
        }


        private GameEntity getRandomEntityAt(float x, float y) {
            GameEntity entity = entitiesClasses.getRandomEntity();
            entity.setPosition(x, y);
            createEntity(entity);
            getStage().addActor(entity);
            return entity;
        }
    }
