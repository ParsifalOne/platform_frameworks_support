/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.media2;

import android.content.Context;
import android.media.session.MediaSessionManager;

import androidx.media2.MediaSession2.ControllerInfo;
import androidx.media2.MediaSession2.SessionCallback;
import androidx.test.filters.SmallTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Tests {@link MediaSessionManager} with {@link MediaSession2} specific APIs.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
@Ignore
public class MediaSessionManager_MediaSession2Test extends MediaSession2TestBase {
    private static final String TAG = "MediaSessionManager_MediaSession2Test";

    private MediaSessionManager mManager;
    private MediaSession2 mSession;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mManager = (MediaSessionManager) mContext.getSystemService(Context.MEDIA_SESSION_SERVICE);

        // Specify TAG here so {@link MediaSession2.getInstance()} doesn't complaint about
        // per test thread differs across the {@link MediaSession2} with the same TAG.
        final MockPlayer player = new MockPlayer(1);
        mSession = new MediaSession2.Builder(mContext, player)
                .setSessionCallback(sHandlerExecutor, new SessionCallback() { })
                .setId(TAG)
                .build();
    }

    @After
    @Override
    public void cleanUp() throws Exception {
        super.cleanUp();
        sHandler.removeCallbacksAndMessages(null);
        mSession.close();
    }

    // TODO(jaewan): Make this host-side test to see per-user behavior.
    @Ignore
    @Test
    public void testGetMediaSession2Tokens_hasMediaController() throws InterruptedException {
        prepareLooper();
        final MockPlayer player = (MockPlayer) mSession.getPlayer();
        player.notifyPlayerStateChanged(SessionPlayer2.PLAYER_STATE_IDLE);

        MediaController2 controller = null;
//        List<SessionToken2> tokens = mManager.getActiveSessionTokens();
//        assertNotNull(tokens);
//        for (int i = 0; i < tokens.size(); i++) {
//            SessionToken2 token = tokens.get(i);
//            if (mContext.getPackageName().equals(token.getPackageName())
//                    && TAG.equals(token.getSessionId())) {
//                assertNull(controller);
//                controller = createController(token);
//            }
//        }
//        assertNotNull(controller);
//
//        // Test if the found controller is correct one.
//        assertEquals(SessionPlayer2.PLAYER_STATE_IDLE, controller.getPlayerState());
//        controller.play();
//
//        assertTrue(player.mCountDownLatch.await(TIMEOUT_MS, TimeUnit.MILLISECONDS));
//        assertTrue(player.mPlayCalled);
    }

    /**
     * Test if server recognizes a session even if the session refuses the connection from server.
     *
     * @throws InterruptedException
     */
    @Test
    public void testGetSessionTokens_sessionRejected() throws InterruptedException {
        prepareLooper();
        mSession.close();
        mSession = new MediaSession2.Builder(mContext, new MockPlayer(0))
                .setId(TAG).setSessionCallback(sHandlerExecutor, new SessionCallback() {
                    @Override
                    public SessionCommandGroup2 onConnect(
                            MediaSession2 session, ControllerInfo controller) {
                        // Reject all connection request.
                        return null;
                    }
                }).build();

        boolean foundSession = false;
//        List<SessionToken2> tokens = mManager.getActiveSessionTokens();
//        assertNotNull(tokens);
//        for (int i = 0; i < tokens.size(); i++) {
//            SessionToken2 token = tokens.get(i);
//            if (mContext.getPackageName().equals(token.getPackageName())
//                    && TAG.equals(token.getSessionId())) {
//                assertFalse(foundSession);
//                foundSession = true;
//            }
//        }
//        assertTrue(foundSession);
    }

    @Test
    public void testGetMediaSession2Tokens_sessionClosed() throws InterruptedException {
        prepareLooper();
        mSession.close();

        // When a session is closed, it should lose binder connection between server immediately.
        // So server will forget the session.
//        List<SessionToken2> tokens = mManager.getActiveSessionTokens();
//        for (int i = 0; i < tokens.size(); i++) {
//            SessionToken2 token = tokens.get(i);
//            assertFalse(mContext.getPackageName().equals(token.getPackageName())
//                    && TAG.equals(token.getSessionId()));
//        }
    }

    @Test
    public void testGetMediaSessionService2Token() throws InterruptedException {
        prepareLooper();
        boolean foundTestSessionService = false;
        boolean foundTestLibraryService = false;
//        List<SessionToken2> tokens = mManager.getSessionServiceTokens();
//        for (int i = 0; i < tokens.size(); i++) {
//            SessionToken2 token = tokens.get(i);
//            if (mContext.getPackageName().equals(token.getPackageName())
//                    && MockMediaSessionService2.ID.equals(token.getSessionId())) {
//                assertFalse(foundTestSessionService);
//                assertEquals(SessionToken2.TYPE_SESSION_SERVICE, token.getType());
//                foundTestSessionService = true;
//            } else if (mContext.getPackageName().equals(token.getPackageName())
//                    && MockMediaLibraryService2.ID.equals(token.getSessionId())) {
//                assertFalse(foundTestLibraryService);
//                assertEquals(SessionToken2.TYPE_LIBRARY_SERVICE, token.getType());
//                foundTestLibraryService = true;
//            }
//        }
//        assertTrue(foundTestSessionService);
//        assertTrue(foundTestLibraryService);
    }

    @Test
    public void testGetAllSessionTokens() throws InterruptedException {
        prepareLooper();
        boolean foundTestSession = false;
        boolean foundTestSessionService = false;
        boolean foundTestLibraryService = false;
//        List<SessionToken2> tokens = mManager.getAllSessionTokens();
//        for (int i = 0; i < tokens.size(); i++) {
//            SessionToken2 token = tokens.get(i);
//            if (!mContext.getPackageName().equals(token.getPackageName())) {
//                continue;
//            }
//            switch (token.getSessionId()) {
//                case TAG:
//                    assertFalse(foundTestSession);
//                    foundTestSession = true;
//                    break;
//                case MockMediaSessionService2.ID:
//                    assertFalse(foundTestSessionService);
//                    foundTestSessionService = true;
//                    assertEquals(SessionToken2.TYPE_SESSION_SERVICE, token.getType());
//                    break;
//                case MockMediaLibraryService2.ID:
//                    assertFalse(foundTestLibraryService);
//                    assertEquals(SessionToken2.TYPE_LIBRARY_SERVICE, token.getType());
//                    foundTestLibraryService = true;
//                    break;
//                default:
//                    fail("Unexpected session " + token + " exists in the package");
//            }
//        }
//        assertTrue(foundTestSession);
//        assertTrue(foundTestSessionService);
//        assertTrue(foundTestLibraryService);
    }

    @Test
    public void testAddOnSessionTokensChangedListener() throws InterruptedException {
//        prepareLooper();
//        TokensChangedListener listener = new TokensChangedListener();
//        mManager.addOnSessionTokensChangedListener(sHandlerExecutor, listener);
//
//        listener.reset();
//        MediaSession2 session1 = new MediaSession2.Builder(mContext)
//                .setPlayer(new MockPlayer(0))
//                .setId(UUID.randomUUID().toString())
//                .build();
//        assertTrue(listener.await());
//        assertTrue(listener.findToken(session1.getToken()));
//
//        listener.reset();
//        session1.close();
//        assertTrue(listener.await());
//        assertFalse(listener.findToken(session1.getToken()));
//
//        listener.reset();
//        MediaSession2 session2 = new MediaSession2.Builder(mContext)
//                .setPlayer(new MockPlayer(0))
//                .setId(UUID.randomUUID().toString())
//                .build();
//        assertTrue(listener.await());
//        assertFalse(listener.findToken(session1.getToken()));
//        assertTrue(listener.findToken(session2.getToken()));
//
//        listener.reset();
//        MediaSession2 session3 = new MediaSession2.Builder(mContext)
//                .setPlayer(new MockPlayer(0))
//                .setId(UUID.randomUUID().toString())
//                .build();
//        assertTrue(listener.await());
//        assertFalse(listener.findToken(session1.getToken()));
//        assertTrue(listener.findToken(session2.getToken()));
//        assertTrue(listener.findToken(session3.getToken()));
//
//        listener.reset();
//        session2.close();
//        assertTrue(listener.await());
//        assertFalse(listener.findToken(session1.getToken()));
//        assertFalse(listener.findToken(session2.getToken()));
//        assertTrue(listener.findToken(session3.getToken()));
//
//        listener.reset();
//        session3.close();
//        assertTrue(listener.await());
//        assertFalse(listener.findToken(session1.getToken()));
//        assertFalse(listener.findToken(session2.getToken()));
//        assertFalse(listener.findToken(session3.getToken()));
//
//        mManager.removeOnSessionTokensChangedListener(listener);
    }

    @Test
    public void testRemoveOnSessionTokensChangedListener() throws InterruptedException {
//        prepareLooper();
//        TokensChangedListener listener = new TokensChangedListener();
//        mManager.addOnSessionTokensChangedListener(sHandlerExecutor, listener);
//
//        listener.reset();
//        MediaSession2 session1 = new MediaSession2.Builder(mContext)
//                .setPlayer(new MockPlayer(0))
//                .setId(UUID.randomUUID().toString())
//                .build();
//        assertTrue(listener.await());
//
//        mManager.removeOnSessionTokensChangedListener(listener);
//
//        listener.reset();
//        session1.close();
//        assertFalse(listener.await());
//
//        listener.reset();
//        MediaSession2 session2 = new MediaSession2.Builder(mContext)
//                .setPlayer(new MockPlayer(0))
//                .setId(UUID.randomUUID().toString())
//                .build();
//        assertFalse(listener.await());
//
//        listener.reset();
//        MediaSession2 session3 = new MediaSession2.Builder(mContext)
//                .setPlayer(new MockPlayer(0))
//                .setId(UUID.randomUUID().toString())
//                .build();
//        assertFalse(listener.await());
//
//        listener.reset();
//        session2.close();
//        assertFalse(listener.await());
//
//        listener.reset();
//        session3.close();
//        assertFalse(listener.await());
    }

//    private class TokensChangedListener implements OnSessionTokensChangedListener {
//        private CountDownLatch mLatch;
//        private List<SessionToken2> mTokens;
//
//        private void reset() {
//            mLatch = new CountDownLatch(1);
//            mTokens = null;
//        }
//
//        private boolean await() throws InterruptedException {
//            return mLatch.await(TIMEOUT_MS, TimeUnit.MILLISECONDS);
//        }
//
//        private boolean findToken(SessionToken2 token) {
//            return mTokens.contains(token);
//        }
//
//        @Override
//        public void onSessionTokensChanged(List<SessionToken2> tokens) {
//            mTokens = tokens;
//            mLatch.countDown();
//        }
//    }
}
