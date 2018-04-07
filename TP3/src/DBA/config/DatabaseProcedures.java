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
        @Override
        public String getQuery(){
            return "{call sp_saveCardsOfWinnerPerMatch (?,?,?,?,?)}";
        }
    },
    SAVEMATCHRESULT{
        @Override
        public String getQuery(){
            return "{call sp_saveMatchResult(?,?,?)}";
        }
    },
    SAVEMATCH{
        @Override
        public String getQuery(){
            return "{call sp_saveMatch(?,?,?,?)}";
        }
    },
    SAVECARD{
        @Override
        public String getQuery(){
            return "{call sp_saveCard(?,?,?)}";
        }
    };
    
    public abstract String getQuery();
    
}
