import { NgModule } from '@angular/core'

import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';

@NgModule({
    exports:[
        NzLayoutModule,
        NzSpinModule,
        NzFormModule,
        NzButtonModule,
        NzInputModule,
    ]
})

export class NgzorroImportsModule{

}