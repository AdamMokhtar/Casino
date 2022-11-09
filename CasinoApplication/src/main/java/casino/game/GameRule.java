package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.cashier.InvalidAmountException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author nagi
 */
public class GameRule implements IGameRule {

    /**
     * Determine the winner from a Set of Bets, using a given random win value;
     * @param randomWinValue  The closet money amount value of a bet to this random number is the winner bet. The number must be a whole number.
     * @param bets  This set of bets that the GameRule have to choose one of them to be the winner of the game.
     * @return Betresult, containing the result for the winning bet. The won amount of the BetResult can not be null or negative value.
     * @throws NoBetsMadeException when no bets have been made yet.
     * @throws IllegalArgumentException when the random win value is not a whole number.
     * @throws InvalidAmountException when the won money amount of the returned BetResult is 0 or negative value.
     *
     * @should throwNoBetsMadeExceptionInCaseThereIsNoBetsInTheBetsList
     * @should throwIllegalArgumentExceptionIfRandomWinValueIsNotAWholeNumber
     * @should notCreateOrReturnABetResultTillTheTotalBetsIsEqualToTheGetMaxBetsPerRound
     * @should callAllTheMoneyAmountOfAllBetsInThePassedListOfBets
     * @should CreateAndReturnABetResultWithTheWinningBetAndTotalMoneyAmountOfBetsAsTheWinningMoneyAmountWhenTotalBetsIsEqualTheGetMaxBetsPerRound
     * @should returnNullInCaseTheTotalBetsAreNotEqualToTheGetMaxBetsPerRound
     * @should throwInvalidAmountExceptionInCaseTheWinningMoneyAmountIs0
     * @should throwInvalidAmountExceptionInCaseTheWinningMoneyAmountIsNegativeValue
     */
    @Override
    public BetResult determineWinner(Integer randomWinValue, Set<Bet> bets) throws NoBetsMadeException, IllegalArgumentException, InvalidAmountException {

        if(bets.size() < 1){
            throw new NoBetsMadeException("Can not determine winner without bets!!!");
        }
        if (randomWinValue < 0){

            throw new IllegalArgumentException("Random win value have to be a whole a number!!");
        }

        if (bets.size() == this.getMaxBetsPerRound()){

            long wonAmount = 0;
            long smallestElement = Math.abs(randomWinValue - bets.stream().findFirst().get().getMoneyAmount().getAmountInCents());
            Bet closestBet = bets.stream().findFirst().get();
            long temp = 0;

            for (Bet i:bets) {
                temp = Math.abs(i.getMoneyAmount().getAmountInCents() - randomWinValue);
                if (smallestElement > temp) {
                    smallestElement = temp;
                    closestBet = i;
                }
                wonAmount += i.getMoneyAmount().getAmountInCents();

            }
            MoneyAmount totalMoneyAmounts = new MoneyAmount(wonAmount);
            BetResult betResult = new BetResult(closestBet, totalMoneyAmounts);

            if (betResult.getAmountWon().getAmountInCents() == 0 || betResult.getAmountWon().getAmountInCents() < 0){

                throw new InvalidAmountException("Winning money amount can not have a 0 value or less!!");
            }

            return betResult;
        }

        return null;

    }

    /**
     * return the maximum number of bets which are used in the calculation of the winner. The GameRule can not determine the winner
     * if the total bets in determineWinner method are not equal to the maximum number. For now we assume 3 is the maximum number therefore, the method return 3.
     *
     * @return
     *
     * @should returnValue3ForMaxBetsPerRound
     */
    @Override
    public int getMaxBetsPerRound() throws IllegalArgumentException {
        return 3;
    }
}
