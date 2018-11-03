/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.com.jinshangcheng.widget.AddressSelector.adapters;

import android.content.Context;

import java.util.List;

import cn.com.jinshangcheng.widget.AddressSelector.AreaModel;

/**
 * The simple Array wheel adapter
 */
public class AreaArrayWheelAdapter extends AbstractWheelTextAdapter {

    // items
    private List<AreaModel> list;

    /**
     * Constructor
     *
     * @param context the current context
     */
    public AreaArrayWheelAdapter(Context context, List<AreaModel> list) {
        super(context);
        // setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.list = list;
    }

    public void setData(List<AreaModel> list) {
        this.list = list;
        notifyDataChangedEvent();
        notifyDataInvalidatedEvent();
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index).name;
        }
        return "";
    }

    @Override
    public int getItemsCount() {
        if (list.isEmpty()) {
            list.add(new AreaModel());
        }
        return list.size();
    }
}
