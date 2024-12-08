package game.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import utilities.FilePaths;

public class AssetManager extends com.badlogic.gdx.assets.AssetManager {
    public void queueAssets() {
        setLoader(TiledMap.class, new TmxMapLoader());

        load(FilePaths.IMAGES + "backgrounds/backgroundsAux.png", Texture.class);
        load(FilePaths.IMAGES + "backgrounds/deathScreen.png", Texture.class);
        load(FilePaths.IMAGES + "backgrounds/bgScreen.png", Texture.class);
        load(FilePaths.IMAGES + "backgrounds/winScreen.png", Texture.class);

        load(FilePaths.IMAGES + "characters/adel/adel0.png", Texture.class);
        load(FilePaths.IMAGES + "characters/adel/adel1.png", Texture.class);
        load(FilePaths.IMAGES + "characters/adel/adel2.png", Texture.class);
        load(FilePaths.IMAGES + "characters/adel/adelDiamond.png", Texture.class);
        load(FilePaths.IMAGES + "characters/adel/blackBullet.png", Texture.class);
        load(FilePaths.IMAGES + "characters/adel/bullet0.png", Texture.class);
        load(FilePaths.IMAGES + "characters/adel/bullet1.png", Texture.class);
        load(FilePaths.IMAGES + "characters/adel/bullet2.png", Texture.class);

        load(FilePaths.IMAGES + "characters/deadEye/deadEye.png", Texture.class);
        load(FilePaths.IMAGES + "characters/deadEye/bullet.png", Texture.class);

        load(FilePaths.IMAGES + "characters/shopKeeper/shopKeeper.png", Texture.class);

        load(FilePaths.IMAGES + "characters/skeleton/bullet.png", Texture.class);
        load(FilePaths.IMAGES + "characters/skeleton/skeleton.png", Texture.class);

        load(FilePaths.IMAGES + "icons/adel.png", Texture.class);
        load(FilePaths.IMAGES + "icons/shop.png", Texture.class);
        load(FilePaths.IMAGES + "icons/skull.png", Texture.class);

        load(FilePaths.IMAGES + "items/candy/candy.png", Texture.class);
        load(FilePaths.IMAGES + "items/randomcard/randomCard.png", Texture.class);
        load(FilePaths.IMAGES + "items/upCard/upCard.png", Texture.class);
        load(FilePaths.IMAGES + "items/bomb.png", Texture.class);
        load(FilePaths.IMAGES + "items/candy.png", Texture.class);
        load(FilePaths.IMAGES + "items/cap.png", Texture.class);
        load(FilePaths.IMAGES + "items/diamond.png", Texture.class);
        load(FilePaths.IMAGES + "items/mushroom.png", Texture.class);
        load(FilePaths.IMAGES + "items/randomCard.png", Texture.class);
        load(FilePaths.IMAGES + "items/shell.png", Texture.class);
        load(FilePaths.IMAGES + "items/skeletonMask.png", Texture.class);
        load(FilePaths.IMAGES + "items/upCard.png", Texture.class);

        load(FilePaths.ROOMS + "boss/boss_1.tmx", TiledMap.class);
        load(FilePaths.ROOMS + "shop/shop.tmx", TiledMap.class);
        load(FilePaths.ROOMS + "stone/map_1.tmx", TiledMap.class);
        load(FilePaths.ROOMS + "stone/map_2.tmx", TiledMap.class);

        load(FilePaths.AUDIO + "game/music/shop.mp3", Music.class);
        load(FilePaths.AUDIO + "game/music/shopIntro.mp3", Music.class);
        load(FilePaths.AUDIO + "game/music/undead.mp3", Music.class);
        load(FilePaths.AUDIO + "game/music/undeadIntro.mp3", Music.class);
        load(FilePaths.AUDIO + "game/death.mp3", Music.class);
        load(FilePaths.AUDIO + "game/playerShoot.mp3", Music.class);
        load(FilePaths.AUDIO + "game/enemyShoot.mp3", Music.class);
        load(FilePaths.AUDIO + "game/bossShoot.mp3", Music.class);
        load(FilePaths.AUDIO + "clickSound.mp3", Music.class);
        load(FilePaths.AUDIO + "hoverSound.mp3", Music.class);
        load(FilePaths.AUDIO + "menuMusic.mp3", Music.class);
        load(FilePaths.AUDIO + "typing.mp3", Music.class);

        load(FilePaths.AUDIO + "songs/deathToEva.mp3", Music.class);
        load(FilePaths.AUDIO + "songs/lostSouls.mp3", Music.class);
        load(FilePaths.AUDIO + "songs/pianoMan.mp3", Music.class);
        load(FilePaths.AUDIO + "songs/spiderFunk.mp3", Music.class);
        load(FilePaths.AUDIO + "songs/tryingToScape.mp3", Music.class);

        finishLoading();
    }
}
