import { GameState, isGameState} from "../types";

export async function startGame(player1: string, player2: string) {
    const response = await fetch("mancala/api/start", {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            player1: player1,
            player2: player2,
        }),
    });

    if (response.ok) {
        const gameState = await response.json();
        return gameState as GameState;
    } else {
        return {
            statusCode: response.status,
            statusText: response.statusText
        };
    }
}

export async function playPit(index: number) {
    const response = await fetch("mancala/api/play", {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            indexToPlay : index,
        }),
    });

    if (response.ok) {
        const result = await response.json();
        return result as GameState;
    } else {
        return {
            statusCode: response.status,
            statusText: response.statusText
        };
    }
}

export function getSeeds(index: number, gameState: GameState | undefined) {
    if (!isGameState(gameState)) {
        return -1
    } else if (index < 7) {
        return gameState.players[0].pits[index].nrOfStones;
    } else {
        return gameState.players[1].pits[index - 7].nrOfStones;
    }
}

