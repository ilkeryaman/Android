package com.reset4.fourwork.library.enums;

import com.reset4.fourwork.constants.Scripts;

/**
 * Created by eilkyam on 23.06.2016.
 */
public enum DBColumnDataType {
    Guid {
        @Override
        public String toString(){
            return Scripts.Guid;
        }
    },
    Integer {
        @Override
        public String toString(){
            return Scripts.Integer;
        }
    },
    Text {
        @Override
        public String toString(){
            return Scripts.Text;
        }
    },
    Boolean {
        @Override
        public String toString(){
            return Scripts.Boolean;
        }
    }
}
