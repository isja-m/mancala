import { useMancalaGame } from "../contexts/MancalaGameContext";
import { PitButton } from "../components/PitButton";
import { getPit } from "../services/api";


export const Play = () => {
    const { gameState, setGameState } = useMancalaGame();
    const pit = getPit(0)
    // print(pit)

    return <div>
        Player 1: {gameState?.players[0].name}<br />
        Player 2: {gameState?.players[1].name}<br />
        <table>
            <tbody>
                <tr>
                    <td></td>
                    <td><PitButton nrOfStones={1} index={0}/></td>
                    <td><PitButton nrOfStones={1} index={1}/></td>
                    <td><PitButton nrOfStones={1} index={2}/></td>
                    <td><PitButton nrOfStones={1} index={3}/></td>
                    <td><PitButton nrOfStones={1} index={4}/></td>
                    <td><PitButton nrOfStones={1} index={5}/></td>
                </tr>
                <tr>
                    <td><PitButton nrOfStones={1} index={13}/></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><PitButton nrOfStones={1} index={6}/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><PitButton nrOfStones={1} index={12}/></td>
                    <td><PitButton nrOfStones={1} index={11}/></td>
                    <td><PitButton nrOfStones={1} index={10}/></td>
                    <td><PitButton nrOfStones={1} index={9}/></td>
                    <td><PitButton nrOfStones={1} index={8}/></td>
                    <td><PitButton nrOfStones={1} index={7}/></td>
                </tr>
            </tbody>
        </table>
    </div>
};