package api.v1.model;

import api.v1.error.BusinessException;
import api.v1.error.Error;

/**
 * Created by kennethlyon on 6/25/16.
 */
public class Type {

    private int id;
    public Type(){
        this.id=-1;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) throws BusinessException{
        if(id<0)
            throw new BusinessException("The Type id cannot be less than 0.", Error.valueOf("INVALID_TASK_NAME_ERROR"));
        else
            this.id=id;
    }
}
