package casino.gamingmachine;


import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.cashier.*;
import casino.game.IGame;
import casino.game.NoBetsMadeException;
import casino.game.NoCurrentRoundException;
import casino.idfactory.BetID;
import casino.idfactory.GamingMachineID;

/**
 * @author nagi
 */
public class GamingMachine extends AbstractGamingMachine{

    private  IGame game;
    private Cashier cashier;
    private Bet openBet;
    private IGamblerCard connectedGamblerCard;
    private GamingMachineID gamingMachineID;

    /**
     * @param game the GamingMachine use it to pass the bet to it and to receive to know who is the winner of the game.
     * @param cashier the GamingMachine use it to check if the bet is valid or not.
     * @throws IllegalArgumentException if one of the parameters is null.
     *
     * @should throwIllegalArgumentExceptionIfOneOfTheParametersIsNull
     * @should initialiseGameAndCashierWithPassedParameters
     */
    public GamingMachine(IGame game, Cashier cashier) throws IllegalArgumentException {
        super(game, cashier);
        if(game == null || cashier == null){
            throw new IllegalArgumentException("Game or Cashier can not be null!!");
        }
        this.game = game;
        this.cashier = cashier;
    }

    /**
     *
     * try to place bet with given amount and connected card.
     * amount needs to be checked with the cashier
     * if accepted: generate a bet using the card and add it to the game.
     *
     * @param amountInCents amount in cents to gamble also, it is used to create the open bet of the machine.
     * @return true if the open bet is valid, excepted and added to betting round.
     * @throws NoPlayerCardException when no card is connected to this machine.
     * @throws CurrentBetMadeException when the machine is already has a open bet
     * @throws InvalidAmountException when passed amountInCents is 0 or less.
     * @throws BetNotExceptedException it is exception of cashier and it is used because cashier method used in this method.
     * @throws NoCurrentRoundException it is exception of game and it is used because game method used in this method.
     * @throws NoBetsMadeException it is needed to call the function acceptBet on Game
     *
     * @should throwNoPlayerCardExceptionInCaseTheConnectedGamblerCardIsNull
     * @should throwCurrentBetMadeExceptionInCaseTheOpenBetIsNotNull
     * @should callGenerateNewBetIdOnGamblerCardToGetABetID
     * @should createNewBetWithTheBetIdOfTheConnectedGamblerCardAndCreateMoneyAmountFromPassedAmountOfCentsThenAssignItToOpenBet
     * @should throwInvalidAmountExceptionInCaseTheAmountInCentsIsNegativeValueOrNull
     * @should returnFalseInCaseTheCheckIfBetIsValidOnCashierReturnFalse
     * @should returnTrueOnlyIfTheCheckIfBetIsValidOnCashierAndTheAcceptBetOnGameReturnTrue
     * @should returnFalseInCaseTheAcceptBetInGameReturnFalse
     *
     */
    @Override
    public boolean placeBet(long amountInCents) throws NoPlayerCardException, CurrentBetMadeException, InvalidAmountException, BetNotExceptedException, NoCurrentRoundException, NoBetsMadeException {

        boolean res = false;

        if (this.getConnectedGamblerCard() == null){

            throw new NoPlayerCardException("Connected card can not be null!!");
        }
        if (this.openBet != null){

            throw new CurrentBetMadeException("There is already placed bet!!");
        }
        if(amountInCents <= 0){

            throw new InvalidAmountException("Money amount in cents can not be 0 or less!!");
        }

        BetID connectCardBetId = this.connectedGamblerCard.generateNewBetID();
        MoneyAmount moneyAmount = new MoneyAmount(amountInCents);

        this.openBet = new Bet(connectCardBetId, moneyAmount);

        if (!this.cashier.checkIfBetIsValid(this.connectedGamblerCard, this.openBet)){
            return false;
        }

        if (this.cashier.checkIfBetIsValid(getConnectedGamblerCard(),this.openBet) && this.game.acceptBet(this.openBet, this)){

            res = true;
        }

        return res;
    }
    /**
     * Accept the BetResult from the winner. Clear all open bets on this machine.
     * When the winner has made his bet on this machine: let the Cashier update
     * the amount of the winner.
     *
     * @param winResult result of a betting round. can be null when there is no winner.
     *
     * @should removeOpenBetInTheConnectedGameInTheMachine
     * @should callAddAmountFromTheCashierToMakeSureTheMoneyIsAddedToWinnerCard
     */
    @Override
    public void acceptWinner(BetResult winResult) throws InvalidAmountException {

        this.openBet = null;
        this.cashier.addAmount(this.getConnectedGamblerCard(), winResult.getAmountWon());

    }
    /**
     * getter
     * @return gamingmachineID
     *
     * @should returnGamingMachineID
     */
    @Override
    public GamingMachineID getGamingMachineID() {
        return this.gamingMachineID;
    }
    /**
     * connect card to this gaming machine
     * @param card to connect
     *
     * @should connectGamblerCardToTheGamingMachine
     * @should throwIllegalArgumentExceptionInCaseThePassedGamblerCardIsNull
     * @should throwIllegalArgumentExceptionInCaseThePassedGamblerCardIsAlreadyConnected
     *
     * @throws IllegalArgumentException when the card is null or already connected to the gaming machine
     */
    @Override
    public void connectCard(IGamblerCard card) throws IllegalArgumentException {

        if(card == null){
            throw new IllegalArgumentException("Placed card can not be null!!");
        }
        if (card == this.connectedGamblerCard){

            throw new IllegalArgumentException("The card is already connected!!");
        }
         this.connectedGamblerCard = card;
    }
    /**
     * disconnects/removes card from this gaming machine.
     * @throws CurrentBetMadeException when open bets made with this card
     * are still present in the current betting round
     *
     * @should setConnectedCardToToNullInCaseTheOpenBetIsNull
     * @should throwCurrentBetMadeExceptionInCaseTheOpenBetIsNotNull
     *
     */
    @Override
    public void disconnectCard() throws CurrentBetMadeException {

        if(this.openBet == null){
            this.connectedGamblerCard = null;
        }else {
            throw new CurrentBetMadeException("The open bet is not null!!");
        }


    }

    public IGame getGame() {
        return game;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public Bet getOpenBet() {
        return openBet;
    }

    public void setGamingMachineID(GamingMachineID gamingMachineID) {
        this.gamingMachineID = gamingMachineID;
    }

    public IGamblerCard getConnectedGamblerCard() {
        return connectedGamblerCard;
    }

    public void setOpenBet(Bet openBet) {
        this.openBet = openBet;
    }

    public void setConnectedGamblerCard(IGamblerCard connectedGamblerCard) {
        this.connectedGamblerCard = connectedGamblerCard;
    }
}
