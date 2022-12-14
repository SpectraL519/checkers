package com.PWr.app.Model.Boards;





public class RussianBoard extends Board implements Cloneable {
    public RussianBoard () {
        this.size = 8;
        this.pawnLines = 3;
        this.fields = new int[this.size][this.size];
    }



    // throws CloneNotSupportedException 
    @Override
    protected Object clone () {
        Board boardClone = new RussianBoard();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                boardClone.fields[i][j] = this.fields[i][j];
            }
        }

        return boardClone;
    }



    // Czy curr i mov sa na planszy
    // Czy na curr jest pion
    // Czy na mov nie ma piona
    // Czy mov jest na ukos
    // std::pion - Jesli skok = 2 => Czy na mov - 1 jest pion przeciwnika
    // std::pion - Czy rusza sie do tyluy bez bicia
    // Czy odpowiedni kolor piona sie rusza
    @Override
    public int checkMove (int rCurr, int cCurr, int rMov, int cMov) {
        // Check if Curr is on the board
        if (!this.isOnBoard(rCurr, cCurr)) {
            return -1;
        }
        
        // Check if Mov is on the board
        if (!this.isOnBoard(rMov, cMov)) {
            return -2;
        }

        // Check if there is a pawn on Curr
        if (this.fields[rCurr][cCurr] == 0) {
            return -3;
        }

        // Check if there is a pawn on Mov
        if (this.fields[rMov][cMov] > 0) {
            System.out.println("Pawn on mov");
            return -4;
        }


        boolean queen = this.isQueen(rCurr, cCurr);

        if (queen) {
            // Here stuff will happen
        }


        int[] step = this.checkPawnStep(rCurr, cCurr, rMov, cMov);
        if (step == null) {
            return -5;
        }

        if (Math.abs(step[0]) == 2) {
            System.out.println("Take!");
            // Take pawn
            this.fields[rCurr + (step[0] / 2)][cCurr + (step[1] / 2)] = 0;

            // Check for further movement possibilities

            // This is for the polish and canadian versions
            // if (1 + this.longestPawnMove(rMov, cMov, this.board.clone()) < this.longestPawnMove(rCurr, cCurr, this.board.clone())) {
            //     return -6;
            // }
            
            try {
                if (this.longestPawnMove(rMov, cMov, this) > 0) {
                    System.out.println("OK: more moves possible");
                    return 2;
                }
    
                System.out.println("OK: no more moves possible");
                return 1;
            }
            catch (CloneNotSupportedException e) {
                System.out.println("Clone error!");
            }
        }

        System.out.println("OK: no more moves possible");
        return 1;
    }
}
