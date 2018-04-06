/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA.config;

/**
 *
 * @author alumno
 */
public enum DatabaseProcedures {
    SAVECARDOFPLAYERPERMATCH{
        public String getQuery(){
            return "{call sp_saveCardsOfWinnerPerMatch (?,?,?,?)}";
        }
    },
    
    saveMatchResult(""),
    saveMatch(""),
    saveCard("");
    storedProc.saveCardOfPlayerPerMatch=
    storedProc.saveMatchResult={call sp_saveMatchResult(?,?,?)}
    storedProc.saveMatch={call sp_saveMatch(?,?,?)}
    storedProc.saveCard={call sp_saveCard(?,?,?)}
    
    
}
