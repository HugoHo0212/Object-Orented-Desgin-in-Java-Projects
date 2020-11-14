package ca.cmpt213.as4.restapi;

import ca.cmpt213.as4.model.Game;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class MazeGameController {
    private List<Game> games = new ArrayList<>();
    private List<ApiGameWrapper> apiGameWrappers = new ArrayList<>();
    private AtomicInteger nextId = new AtomicInteger();

    @GetMapping("/about")
    public String getAuthorName() {
        return "Yuansheng He";
    }

    @GetMapping("/games")
    public List<ApiGameWrapper> getGames() {
        return apiGameWrappers;
    }

    @PostMapping("/games")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApiGameWrapper createNewGame() {
        Game game = new Game(15, 20);
        ApiGameWrapper apiGameWrapper = ApiGameWrapper.makeFromGame(game, nextId.getAndIncrement());
        apiGameWrappers.add(apiGameWrapper);
        games.add(game);
        return apiGameWrapper;
    }

    @GetMapping("games/{id}")
    public ApiGameWrapper getOneGame(@PathVariable("id") int gameId) {
        for (ApiGameWrapper apiGameWrapper : apiGameWrappers) {
            if (apiGameWrapper.gameNumber == gameId) {
                return apiGameWrapper;
            }
        }

        throw new IllegalArgumentException();
    }

    @GetMapping("games/{id}/board")
    public ApiBoardWrapper getOneBoard(@PathVariable("id") int gameId) {
        for (ApiGameWrapper apiGameWrapper : apiGameWrappers) {
            if (apiGameWrapper.gameNumber == gameId) {
                int index = apiGameWrapper.gameNumber;
                Game game = games.get(index);
                ApiBoardWrapper apiBoardWrapper = ApiBoardWrapper.makeFromGame(game);
                return apiBoardWrapper;
            }
        }

        throw new IllegalArgumentException();
    }

    @PostMapping("games/{id}/moves")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void moveGameElements(@PathVariable("id") int gameId, @RequestBody String move) {
        for (ApiGameWrapper apiGameWrapper : apiGameWrappers) {
            if (apiGameWrapper.gameNumber == gameId) {
                int index = apiGameWrapper.gameNumber;
                Game game = games.get(index);
                if (move.equals("MOVE_CATS")) {
                    game.moveCatsRandomly();
                    apiGameWrapper.isGameLost = game.hasUserLost();
                } else if (move.equals("MOVE_UP") && game.isMouseUpLegal()) {
                    if (game.isMouseUpLegal()) {
                        game.moveMouseUp();
                        game.revealCellsAroundMouse();
                    }
                } else if (move.equals("MOVE_DOWN") && game.isMouseDownLegal()) {
                    if (game.isMouseDownLegal()) {
                        game.moveMouseDown();
                        game.revealCellsAroundMouse();
                    }
                } else if (move.equals("MOVE_LEFT") && game.isMouseLeftLegal()) {
                    if (game.isMouseLeftLegal()) {
                        game.moveMouseLeft();
                        game.revealCellsAroundMouse();
                    }
                } else if (move.equals("MOVE_RIGHT") && game.isMouseRightLegal()) {
                    if (game.isMouseRightLegal()) {
                        game.moveMouseRight();
                        game.revealCellsAroundMouse();
                    }
                } else {
                    throw new IllegalStateException();
                }
                if (game.isCheeseEaten()) {
                    game.increaseCollectedCheese();
                    game.generateCheese();
                }
                apiGameWrapper.isGameLost = game.hasUserLost();
                apiGameWrapper.isGameWon = game.hasUserWon();
                apiGameWrapper.numCheeseFound = game.getNumCheeseCollected();
                return;
            }
        }

        throw new IllegalArgumentException();
    }

    @PostMapping("games/{id}/cheatstate")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void activateCheatState(@PathVariable("id") int gameId, @RequestBody String cheatState) {
        for (ApiGameWrapper apiGameWrapper : apiGameWrappers) {
            if (apiGameWrapper.gameNumber == gameId) {
                int index = apiGameWrapper.gameNumber;
                Game game = games.get(index);
               if (cheatState.equals("1_CHEESE")) {
                   game.setNumCheeseGoal(1);
                   apiGameWrapper.numCheeseGoal = game.getNumCheeseToCollect();
                   apiGameWrapper.isGameWon = game.hasUserWon();
               } else if (cheatState.equals("SHOW_ALL")) {
                   game.revealMaze();
               } else {
                   throw new IllegalStateException();
               }
               return;
            }
        }

        throw new IllegalArgumentException();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND,
                reason ="Request ID not found.")
    public void badIdException() {

    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
                reason = "Bad Request.")
    public void badKeyInputException() {

    }
}
